package com.mulesoft.connectors.inference.internal.helpers.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.inference.api.request.ChatPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.DefaultRequestPayloadRecord;
import com.mulesoft.connectors.inference.api.request.FunctionDefinitionRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.TextGenerationRequestPayloadDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class RequestPayloadHelper {
    private static final Logger logger = LoggerFactory.getLogger(RequestPayloadHelper.class);

    protected final ObjectMapper objectMapper;

    public RequestPayloadHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public TextGenerationRequestPayloadDTO buildChatAnswerPromptPayload(TextGenerationConnection connection, String prompt) {
        return buildPayload(
                connection,
                List.of(
                        new ChatPayloadRecord("user",prompt)),null);
    }

    public TextGenerationRequestPayloadDTO buildPayload(TextGenerationConnection connection, List<ChatPayloadRecord> messagesArray,
                                                        List<FunctionDefinitionRecord> tools) {
        return new DefaultRequestPayloadRecord(connection.getModelName(),
                messagesArray,
                connection.getMaxTokens(),
                connection.getTemperature(),
                connection.getTopP(),
                tools);
    }

    public List<ChatPayloadRecord> parseInputStreamToChatList(InputStream inputStream) throws IOException {

        return objectMapper.readValue(
                inputStream,
                objectMapper.getTypeFactory()
                        .constructCollectionType(List.class, ChatPayloadRecord.class));
    }

    public TextGenerationRequestPayloadDTO buildPromptTemplatePayload(TextGenerationConnection connection, String template, String instructions, String data) {

        List<ChatPayloadRecord> messagesArray = createMessagesArrayWithSystemPrompt(
                connection, template + " - " + instructions, data);

        return buildPayload(connection, messagesArray,null);
    }

    public List<ChatPayloadRecord> createMessagesArrayWithSystemPrompt(
            TextGenerationConnection connection, String systemContent, String userContent) {

        // Create system/assistant message based on provider
        ChatPayloadRecord systemMessage = new ChatPayloadRecord(
                "ANTHROPIC".equals(connection.getInferenceType()) ? "assistant" : "system",
                systemContent);

        // Create user message
        ChatPayloadRecord userMessage = new ChatPayloadRecord("user",userContent);

        return List.of(systemMessage,userMessage);
    }

    public String buildToolsTemplatePayload(TextGenerationConnection connection, String template,
                                                       String instructions, String data, InputStream tools) throws IOException {

        List<FunctionDefinitionRecord> toolsRecord = parseInputStreamToTools(tools);

        logger.debug("toolsArray: {}", toolsRecord);

        List<ChatPayloadRecord> messagesArray = createMessagesArrayWithSystemPrompt(
                connection, template + " - " + instructions, data);

        return connection.getObjectMapper()
                .writeValueAsString(buildPayload(connection, messagesArray, toolsRecord));
    }

    public List<FunctionDefinitionRecord> parseInputStreamToTools(InputStream inputStream) throws IOException {

        return objectMapper.readValue(
                inputStream,
                objectMapper.getTypeFactory()
                        .constructCollectionType(List.class,FunctionDefinitionRecord.class));
    }
}
