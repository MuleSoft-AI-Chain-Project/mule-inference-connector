package com.mulesoft.connectors.inference.internal.helpers.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.inference.api.request.ChatPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.DefaultRequestPayloadRecord;
import com.mulesoft.connectors.inference.api.request.FunctionDefinitionRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.TextGenerationRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.VertexAIAnthropicChatPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.anthropic.VertexAIAnthropicPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.google.PartRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.google.SystemInstructionRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.google.UserContentRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.google.VertexAIGoogleChatPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.google.VertexAIGoogleGenerationConfigRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.google.VertexAIGooglePayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.meta.VertexAIMetaPayloadRecord;
import com.mulesoft.connectors.inference.internal.utils.ProviderUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import static com.mulesoft.connectors.inference.internal.utils.ProviderUtils.*;

public class VertexAIRequestPayloadHelper extends RequestPayloadHelper {

    public static final String VERTEX_AI_ANTHROPIC_VERSION_VALUE = "vertex-2023-10-16";

    public VertexAIRequestPayloadHelper(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public TextGenerationRequestPayloadDTO buildChatAnswerPromptPayload(TextGenerationConnection connection, String prompt) {

        String provider = ProviderUtils.getProviderByModel(connection.getModelName());

        return switch (provider) {
            case GOOGLE_PROVIDER_TYPE -> buildVertexAIGooglePayload(
                    connection,
                    prompt,
                    Collections.emptyList(),
                    null,
                    Collections.emptyList());
            case ANTHROPIC_PROVIDER_TYPE  -> getAnthropicRequestPayloadDTO(connection, prompt,null);
            default -> getDefaultRequestPayloadDTO(connection, List.of(new ChatPayloadRecord("user", prompt)));
        };
    }

    @Override
    public TextGenerationRequestPayloadDTO buildPayload(TextGenerationConnection connection, List<ChatPayloadRecord> messagesArray, List<FunctionDefinitionRecord> tools) {

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
    public TextGenerationRequestPayloadDTO buildPromptTemplatePayload(TextGenerationConnection connection, String template, String instructions, String data) {

        String provider = ProviderUtils.getProviderByModel(connection.getModelName());

        return switch (provider) {
            case GOOGLE_PROVIDER_TYPE -> {
                PartRecord partRecord = new PartRecord(template + " - " + instructions);
                SystemInstructionRecord systemInstructionRecord = new SystemInstructionRecord(List.of(partRecord));
                yield buildVertexAIGooglePayload(
                        connection,
                        data,
                        Collections.emptyList(),
                        systemInstructionRecord,
                        Collections.emptyList());
            }
            case ANTHROPIC_PROVIDER_TYPE ->
                    getAnthropicRequestPayloadDTO(connection, data,template + " - " + instructions);
            default -> {
                List<ChatPayloadRecord> messagesArray = createMessagesArrayWithSystemPrompt(
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

        VertexAIAnthropicChatPayloadRecord vertexAIAnthropicChatPayloadRecord = new VertexAIAnthropicChatPayloadRecord("text", prompt);

        ChatPayloadRecord payloadDTO;
        try {
            payloadDTO = new ChatPayloadRecord("user",
                    objectMapper.writeValueAsString(
                            List.of(vertexAIAnthropicChatPayloadRecord)));
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

    private DefaultRequestPayloadRecord getDefaultRequestPayloadDTO(TextGenerationConnection connection, List<ChatPayloadRecord> chatPayloadRecordList) {
        return new DefaultRequestPayloadRecord(connection.getModelName(),
                chatPayloadRecordList,
                connection.getMaxTokens(),
                connection.getTemperature(),
                connection.getTopP(),null);
    }

    private VertexAIGoogleChatPayloadRecord buildVertexAIGooglePayload(TextGenerationConnection connection, String prompt,
                                                                       List<String> safetySettings,
                                                                       SystemInstructionRecord systemInstruction,
                                                                       List<String> tools) {
        PartRecord partRecord = new PartRecord(prompt);
        UserContentRecord userContentRecord = new UserContentRecord("user",List.of(partRecord));

        //create the generationConfig
        VertexAIGoogleGenerationConfigRecord generationConfig = buildVertexAIGoogleGenerationConfig(connection);

        return new VertexAIGoogleChatPayloadRecord(List.of(userContentRecord),
                systemInstruction,
                generationConfig,
                safetySettings != null && !safetySettings.isEmpty() ? safetySettings:null,
                tools != null && !tools.isEmpty() ? tools:null);
    }

    private VertexAIGoogleGenerationConfigRecord buildVertexAIGoogleGenerationConfig(TextGenerationConnection connection) {
        //create the generationConfig
        return new VertexAIGoogleGenerationConfigRecord(List.of("TEXT"), connection.getTemperature(),
                connection.getMaxTokens(),
                connection.getTopP());
    }

}
