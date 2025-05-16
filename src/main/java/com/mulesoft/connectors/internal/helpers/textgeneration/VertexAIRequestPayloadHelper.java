package com.mulesoft.connectors.internal.helpers.textgeneration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.internal.constants.InferenceConstants;
import com.mulesoft.connectors.internal.dto.VertexAIAnthropicChatPayloadDTO;
import com.mulesoft.connectors.internal.dto.ChatPayloadDTO;
import com.mulesoft.connectors.internal.dto.DefaultRequestPayloadDTO;
import com.mulesoft.connectors.internal.dto.RequestPayloadDTO;
import com.mulesoft.connectors.internal.dto.vertexai.anthropic.VertexAIAnthropicPayloadRecord;
import com.mulesoft.connectors.internal.dto.vertexai.google.*;
import com.mulesoft.connectors.internal.dto.vertexai.meta.VertexAIMetaPayloadRecord;
import com.mulesoft.connectors.internal.helpers.RequestPayloadHelper;
import com.mulesoft.connectors.internal.utils.ProviderUtils;

import java.util.Collections;
import java.util.List;

public class VertexAIRequestPayloadHelper extends RequestPayloadHelper {

    public VertexAIRequestPayloadHelper(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public RequestPayloadDTO buildChatAnswerPromptPayload(TextGenerationConnection connection, String prompt) {

        String provider = ProviderUtils.getProviderByModel(connection.getModelName());

        return switch (provider) {
            case "Google" -> buildVertexAIGooglePayload(
                    connection,
                    prompt,
                    Collections.emptyList(),
                    null,
                    Collections.emptyList());
            case "Anthropic" -> getAnthropicRequestPayloadDTO(connection, prompt);
            default -> getDefaultRequestPayloadDTO(connection, List.of(new ChatPayloadDTO("user", prompt)));
        };
    }

    @Override
    public RequestPayloadDTO buildPayload(TextGenerationConnection connection, List<ChatPayloadDTO> messagesArray) {

        String provider = ProviderUtils.getProviderByModel(connection.getModelName());

        return switch (provider) {
            case "Google" -> new VertexAIGooglePayloadRecord(messagesArray,
                    null,
                    buildVertexAIGoogleGenerationConfig(connection),
                    null,
                    null);
            case "Anthropic" -> new VertexAIAnthropicPayloadRecord(InferenceConstants.VERTEX_AI_ANTHROPIC_VERSION_VALUE,
                    messagesArray,
                    connection.getMaxTokens());
            case "Meta" -> new VertexAIMetaPayloadRecord(InferenceConstants.VERTEX_AI_ANTHROPIC_VERSION_VALUE,
                    messagesArray,
                    connection.getMaxTokens(),false);
            default -> getDefaultRequestPayloadDTO(connection,messagesArray);
        };
    }

    private DefaultRequestPayloadDTO getAnthropicRequestPayloadDTO(TextGenerationConnection connection, String prompt) {
        VertexAIAnthropicChatPayloadDTO vertexAIAnthropicChatPayloadDTO = new VertexAIAnthropicChatPayloadDTO("text", prompt);

        ChatPayloadDTO payloadDTO;
        try {
            payloadDTO = new ChatPayloadDTO("user",
                    objectMapper.writeValueAsString(
                            List.of(vertexAIAnthropicChatPayloadDTO)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return getDefaultRequestPayloadDTO(connection,List.of(payloadDTO));
    }

    private DefaultRequestPayloadDTO getDefaultRequestPayloadDTO(TextGenerationConnection connection, List<ChatPayloadDTO> chatPayloadDTOList) {
        return new DefaultRequestPayloadDTO(connection.getModelName(),
                chatPayloadDTOList,
                connection.getMaxTokens());
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
        return new VertexAIGoogleGenerationConfigDTO(new String[]{"TEXT"}, connection.getTemperature(),
                connection.getMaxTokens(),
                connection.getTopP());
    }

}
