package com.mulesoft.connectors.internal.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.internal.dto.ChatPayloadDTO;
import com.mulesoft.connectors.internal.dto.DefaultRequestPayloadDTO;
import com.mulesoft.connectors.internal.dto.RequestPayloadDTO;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class RequestPayloadHelper {

    protected final ObjectMapper objectMapper;

    public RequestPayloadHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public RequestPayloadDTO buildChatAnswerPromptPayload(TextGenerationConnection connection, String prompt) {
        return buildPayload(
                connection,
                List.of(
                        new ChatPayloadDTO("user",prompt)));
    }

    public RequestPayloadDTO buildPayload(TextGenerationConnection connection, List<ChatPayloadDTO> messagesArray) {
        return new DefaultRequestPayloadDTO(connection.getModelName(),
                messagesArray,
                connection.getMaxTokens(),
                connection.getTemperature(),
                connection.getTopP());
    }

    public List<ChatPayloadDTO> parseInputStreamToJsonArray(InputStream inputStream) throws IOException {

        return objectMapper.readValue(
                inputStream,
                objectMapper.getTypeFactory()
                        .constructCollectionType(List.class,ChatPayloadDTO.class));
    }

    public RequestPayloadDTO buildPromptTemplatePayload(TextGenerationConnection connection, String template, String instructions, String data) {

        List<ChatPayloadDTO> messagesArray = createMessagesArrayWithSystemPrompt(
                connection, template + " - " + instructions, data);

        return buildPayload(connection, messagesArray);
    }

    public List<ChatPayloadDTO> createMessagesArrayWithSystemPrompt(
            TextGenerationConnection connection, String systemContent, String userContent) {

        // Create system/assistant message based on provider
        ChatPayloadDTO systemMessage = new ChatPayloadDTO(
                "ANTHROPIC".equals(connection.getInferenceType()) ? "assistant" : "system",
                systemContent);

        // Create user message
        ChatPayloadDTO userMessage = new ChatPayloadDTO("user",userContent);

        return List.of(systemMessage,userMessage);
    }
}
