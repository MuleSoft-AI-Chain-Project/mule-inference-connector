package com.mulesoft.connectors.internal.helpers.textgeneration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.internal.dto.AnthropicChatPayloadDTO;
import com.mulesoft.connectors.internal.dto.ChatPayloadDTO;
import com.mulesoft.connectors.internal.dto.DefaultRequestPayloadDTO;
import com.mulesoft.connectors.internal.dto.RequestPayloadDTO;
import com.mulesoft.connectors.internal.helpers.RequestPayloadHelper;

import java.util.List;

public class AnthropicRequestPayloadHelper extends RequestPayloadHelper {

    public AnthropicRequestPayloadHelper(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public RequestPayloadDTO buildChatAnswerPromptPayload(TextGenerationConnection connection, String prompt) {

        AnthropicChatPayloadDTO anthropicChatPayloadDTO = new AnthropicChatPayloadDTO("text",prompt);

        ChatPayloadDTO payloadDTO;
        try {
            payloadDTO = new ChatPayloadDTO("user",objectMapper.writeValueAsString(List.of(anthropicChatPayloadDTO)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return buildPayload(connection, List.of(payloadDTO));
    }

    @Override
    public DefaultRequestPayloadDTO buildPayload(TextGenerationConnection connection, List<ChatPayloadDTO> messagesArray) {

        return new DefaultRequestPayloadDTO(connection.getModelName(),
                messagesArray,
                connection.getMaxTokens());
    }

}
