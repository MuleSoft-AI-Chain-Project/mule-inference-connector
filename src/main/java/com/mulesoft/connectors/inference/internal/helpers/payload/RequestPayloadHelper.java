package com.mulesoft.connectors.inference.internal.helpers.payload;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.api.request.ChatPayloadRecord;
import com.mulesoft.connectors.inference.api.request.FunctionDefinitionRecord;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.connection.VisionModelConnection;
import com.mulesoft.connectors.inference.internal.dto.imagegeneration.DefaultImageRequestPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.imagegeneration.ImageGenerationRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.moderation.ModerationRequestPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.DefaultRequestPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.TextGenerationRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.vision.Content;
import com.mulesoft.connectors.inference.internal.dto.vision.DefaultVisionRequestPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.vision.ImageUrl;
import com.mulesoft.connectors.inference.internal.dto.vision.ImageUrlContent;
import com.mulesoft.connectors.inference.internal.dto.vision.Message;
import com.mulesoft.connectors.inference.internal.dto.vision.TextContent;
import com.mulesoft.connectors.inference.internal.dto.vision.VisionRequestPayloadDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Base64;
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

    public TextGenerationRequestPayloadDTO buildToolsTemplatePayload(TextGenerationConnection connection, String template,
                                                                     String instructions, String data, InputStream tools) throws IOException {

        List<FunctionDefinitionRecord> toolsRecord = parseInputStreamToTools(tools);

        logger.debug("toolsArray: {}", toolsRecord);

        return buildToolsTemplatePayload(connection, template, instructions, data, toolsRecord);
    }

    public TextGenerationRequestPayloadDTO buildToolsTemplatePayload(TextGenerationConnection connection, String template,
                                                                     String instructions, String data, List<FunctionDefinitionRecord> tools) throws IOException {

        List<ChatPayloadRecord> messagesArray = createMessagesArrayWithSystemPrompt(
                connection, template + " - " + instructions, data);

        return buildPayload(connection, messagesArray, tools);
    }

    public ImageGenerationRequestPayloadDTO createRequestImageGeneration(String model, String prompt) {

        return new DefaultImageRequestPayloadRecord(prompt,"b64_json");
    }

    public VisionRequestPayloadDTO createRequestImageURL(VisionModelConnection connection, String prompt, String imageUrl) throws IOException {

        List<Content> contentArray = new ArrayList<>();

        contentArray.add(new TextContent("text", prompt));
        contentArray.add(new ImageUrlContent("image_url", new ImageUrl(getImageUrl(imageUrl))));

        // Create user message
        Message message = new Message("user", contentArray);
        return new DefaultVisionRequestPayloadRecord(connection.getModelName(),
                List.of(message),
                connection.getMaxTokens(),
                connection.getTemperature(),
                connection.getTopP());
    }

    public ModerationRequestPayloadRecord getModerationRequestPayload(String modelName, InputStream text) throws IOException {
            Object input = objectMapper.readValue(text, Object.class);
            return new ModerationRequestPayloadRecord(input, modelName);
    }

    protected List<ChatPayloadRecord> createMessagesArrayWithSystemPrompt(
            TextGenerationConnection connection, String systemContent, String userContent) {

        // Create system/assistant message based on provider
        ChatPayloadRecord systemMessage = new ChatPayloadRecord(
                "ANTHROPIC".equals(connection.getInferenceType()) ? "assistant" : "system",
                systemContent);

        // Create user message
        ChatPayloadRecord userMessage = new ChatPayloadRecord("user",userContent);

        return List.of(systemMessage,userMessage);
    }

    protected List<FunctionDefinitionRecord> parseInputStreamToTools(InputStream inputStream) throws IOException {

        return objectMapper.readValue(
                inputStream,
                objectMapper.getTypeFactory()
                        .constructCollectionType(List.class,FunctionDefinitionRecord.class));
    }

    protected String getMimeType(String base64String) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(decodedBytes);
        String mimeType = URLConnection.guessContentTypeFromStream(inputStream);
        return mimeType != null ? mimeType : "image/jpeg";
    }

    protected boolean isBase64String(String str) {
        if (str == null || str.length() % 4 != 0 || !str.matches("^[A-Za-z0-9+/]*={0,2}$")) {
            return false;
        }
        try {
            Base64.getDecoder().decode(str);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private String getImageUrl(String imageUrl) throws IOException {
        return isBase64String(imageUrl)
                ? "data:" + getMimeType(imageUrl) + ";base64," + imageUrl
                : imageUrl;
    }
}
