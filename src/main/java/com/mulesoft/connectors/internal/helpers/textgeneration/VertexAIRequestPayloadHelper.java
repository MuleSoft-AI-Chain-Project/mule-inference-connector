package com.mulesoft.connectors.internal.helpers.textgeneration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.internal.dto.ChatPayloadDTO;
import com.mulesoft.connectors.internal.dto.DefaultRequestPayloadDTO;
import com.mulesoft.connectors.internal.dto.FunctionDefinitionRecord;
import com.mulesoft.connectors.internal.dto.RequestPayloadDTO;
import com.mulesoft.connectors.internal.dto.VertexAIAnthropicChatPayloadDTO;
import com.mulesoft.connectors.internal.dto.vertexai.anthropic.VertexAIAnthropicPayloadRecord;
import com.mulesoft.connectors.internal.dto.vertexai.google.PartRecord;
import com.mulesoft.connectors.internal.dto.vertexai.google.SystemInstructionDTO;
import com.mulesoft.connectors.internal.dto.vertexai.google.UserContentRecord;
import com.mulesoft.connectors.internal.dto.vertexai.google.VertexAIGoogleChatPayloadRecord;
import com.mulesoft.connectors.internal.dto.vertexai.google.VertexAIGoogleGenerationConfigDTO;
import com.mulesoft.connectors.internal.dto.vertexai.google.VertexAIGooglePayloadRecord;
import com.mulesoft.connectors.internal.dto.vertexai.meta.VertexAIMetaPayloadRecord;
import com.mulesoft.connectors.internal.helpers.RequestPayloadHelper;
import com.mulesoft.connectors.internal.utils.ProviderUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import static com.mulesoft.connectors.internal.utils.ProviderUtils.*;

public class VertexAIRequestPayloadHelper extends RequestPayloadHelper {

    public static final String VERTEX_AI_ANTHROPIC_VERSION_VALUE = "vertex-2023-10-16";

    public VertexAIRequestPayloadHelper(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public RequestPayloadDTO buildChatAnswerPromptPayload(TextGenerationConnection connection, String prompt) {

        String provider = ProviderUtils.getProviderByModel(connection.getModelName());

        return switch (provider) {
            case GOOGLE_PROVIDER_TYPE -> buildVertexAIGooglePayload(
                    connection,
                    prompt,
                    Collections.emptyList(),
                    null,
                    Collections.emptyList());
            case ANTHROPIC_PROVIDER_TYPE  -> getAnthropicRequestPayloadDTO(connection, prompt,null);
            default -> getDefaultRequestPayloadDTO(connection, List.of(new ChatPayloadDTO("user", prompt)));
        };
    }

    @Override
    public RequestPayloadDTO buildPayload(TextGenerationConnection connection, List<ChatPayloadDTO> messagesArray,List<FunctionDefinitionRecord> tools) {

        String provider = ProviderUtils.getProviderByModel(connection.getModelName());

        return switch (provider) {
            case GOOGLE_PROVIDER_TYPE -> new VertexAIGooglePayloadRecord(messagesArray,
                    null,
                    buildVertexAIGoogleGenerationConfig(connection),
                    null,
                    tools);
            case ANTHROPIC_PROVIDER_TYPE -> new VertexAIAnthropicPayloadRecord(VERTEX_AI_ANTHROPIC_VERSION_VALUE,
                    messagesArray,
                    connection.getMaxTokens(),
                    connection.getTemperature(),
                    connection.getTopP(),
                    null);
            case META_PROVIDER_TYPE -> new VertexAIMetaPayloadRecord(VERTEX_AI_ANTHROPIC_VERSION_VALUE,
                    messagesArray,
                    connection.getMaxTokens(),
                    connection.getTemperature(),
                    connection.getTopP(),
                    false);
            default -> getDefaultRequestPayloadDTO(connection,messagesArray);
        };
    }

    @Override
    public RequestPayloadDTO buildPromptTemplatePayload(TextGenerationConnection connection, String template, String instructions, String data) {

        String provider = ProviderUtils.getProviderByModel(connection.getModelName());

        return switch (provider) {
            case GOOGLE_PROVIDER_TYPE -> {
                PartRecord partRecord = new PartRecord(template + " - " + instructions);
                SystemInstructionDTO systemInstructionDTO = new SystemInstructionDTO(List.of(partRecord));
                yield buildVertexAIGooglePayload(
                        connection,
                        data,
                        Collections.emptyList(),
                        systemInstructionDTO,
                        Collections.emptyList());
            }
            case ANTHROPIC_PROVIDER_TYPE ->
                    getAnthropicRequestPayloadDTO(connection, data,template + " - " + instructions);
            default -> {
                List<ChatPayloadDTO> messagesArray = createMessagesArrayWithSystemPrompt(
                        connection, template + " - " + instructions, data);

                yield buildPayload(connection, messagesArray,null);
            }
        };
    }

    @Override
    public String buildToolsTemplatePayload(TextGenerationConnection connection, String template,
                                            String instructions, String data, InputStream tools) throws IOException {
        String provider = ProviderUtils.getProviderByModel(connection.getModelName());

        throw new IllegalArgumentException(provider + ":" + connection.getModelName() + " on Vertex AI do not currently support function calling at this time.");
    }

    private VertexAIAnthropicPayloadRecord getAnthropicRequestPayloadDTO(TextGenerationConnection connection, String prompt, String system) {

        VertexAIAnthropicChatPayloadDTO vertexAIAnthropicChatPayloadDTO = new VertexAIAnthropicChatPayloadDTO("text", prompt);

        ChatPayloadDTO payloadDTO;
        try {
            payloadDTO = new ChatPayloadDTO("user",
                    objectMapper.writeValueAsString(
                            List.of(vertexAIAnthropicChatPayloadDTO)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new VertexAIAnthropicPayloadRecord(VERTEX_AI_ANTHROPIC_VERSION_VALUE,
                List.of(payloadDTO),
                connection.getMaxTokens(),
                connection.getTemperature(),
                connection.getTopP(),
                system);
    }

    private DefaultRequestPayloadDTO getDefaultRequestPayloadDTO(TextGenerationConnection connection, List<ChatPayloadDTO> chatPayloadDTOList) {
        return new DefaultRequestPayloadDTO(connection.getModelName(),
                chatPayloadDTOList,
                connection.getMaxTokens(),
                connection.getTemperature(),
                connection.getTopP(),null);
    }

    private VertexAIGoogleChatPayloadRecord buildVertexAIGooglePayload(TextGenerationConnection connection, String prompt,
                                                                       List<String> safetySettings,
                                                                       SystemInstructionDTO systemInstruction,
                                                                       List<String> tools) {
        PartRecord partRecord = new PartRecord(prompt);
        UserContentRecord userContentRecord = new UserContentRecord("user",List.of(partRecord));

        //create the generationConfig
        VertexAIGoogleGenerationConfigDTO generationConfig = buildVertexAIGoogleGenerationConfig(connection);

        return new VertexAIGoogleChatPayloadRecord(List.of(userContentRecord),
                systemInstruction,
                generationConfig,
                safetySettings != null && !safetySettings.isEmpty() ? safetySettings:null,
                tools != null && !tools.isEmpty() ? tools:null);
    }

    private VertexAIGoogleGenerationConfigDTO buildVertexAIGoogleGenerationConfig(TextGenerationConnection connection) {
        //create the generationConfig
        return new VertexAIGoogleGenerationConfigDTO(List.of("TEXT"), connection.getTemperature(),
                connection.getMaxTokens(),
                connection.getTopP());
    }

}
