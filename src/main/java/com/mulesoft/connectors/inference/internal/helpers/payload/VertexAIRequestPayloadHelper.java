package com.mulesoft.connectors.inference.internal.helpers.payload;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.api.request.ChatPayloadRecord;
import com.mulesoft.connectors.inference.api.request.FunctionDefinitionRecord;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.DefaultRequestPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.TextGenerationRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.VertexAIAnthropicChatPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.anthropic.VertexAIAnthropicPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.google.*;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.meta.VertexAIMetaPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.vision.*;
import com.mulesoft.connectors.inference.internal.dto.vision.vertexai.FileData;
import com.mulesoft.connectors.inference.internal.dto.vision.vertexai.InlineData;
import com.mulesoft.connectors.inference.internal.dto.vision.vertexai.Part;
import com.mulesoft.connectors.inference.internal.dto.vision.vertexai.VisionContentRecord;
import com.mulesoft.connectors.inference.internal.exception.InferenceErrorType;
import com.mulesoft.connectors.inference.internal.utils.ProviderUtils;
import org.mule.runtime.extension.api.exception.ModuleException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.mulesoft.connectors.inference.internal.utils.ProviderUtils.ANTHROPIC_PROVIDER_TYPE;
import static com.mulesoft.connectors.inference.internal.utils.ProviderUtils.GOOGLE_PROVIDER_TYPE;
import static com.mulesoft.connectors.inference.internal.utils.ProviderUtils.META_PROVIDER_TYPE;

public class VertexAIRequestPayloadHelper extends RequestPayloadHelper {

    public static final String VERTEX_AI_ANTHROPIC_VERSION_VALUE = "vertex-2023-10-16";
    private static final String DEFAULT_MIME_TYPE = "image/jpeg";

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
            case GOOGLE_PROVIDER_TYPE -> new VertexAIGooglePayloadRecord<>(messagesArray,
                    null,
                    buildVertexAIGoogleGenerationConfig(connection),
                    null,
                    tools);
            case ANTHROPIC_PROVIDER_TYPE -> new VertexAIAnthropicPayloadRecord<>(VERTEX_AI_ANTHROPIC_VERSION_VALUE,
                    messagesArray,
                    connection.getMaxTokens(),
                    connection.getTemperature(),
                    connection.getTopP(),
                    null);
            case META_PROVIDER_TYPE -> new VertexAIMetaPayloadRecord<>(VERTEX_AI_ANTHROPIC_VERSION_VALUE,
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

    @Override
    public VisionRequestPayloadDTO createRequestImageURL(TextGenerationConnection connection, String prompt, String imageUrl) throws IOException {

        String provider = ProviderUtils.getProviderByModel(connection.getModelName());

        Object content =  switch (provider) {
            case GOOGLE_PROVIDER_TYPE -> getGoogleVisionContentRecord(prompt, imageUrl);
            case ANTHROPIC_PROVIDER_TYPE -> getAnthropicVisionContentRecord(
                    connection,prompt,imageUrl);
            default -> throw new IllegalArgumentException("Unknown provider");
        };

        return buildPayload(connection, List.of(content));
    }

    private VisionContentRecord getGoogleVisionContentRecord(String prompt, String imageUrl) throws IOException {
        List<Part> parts = new ArrayList<>();

        if (isBase64String(imageUrl)) {
            InlineData inlineData = new InlineData(getMimeType(imageUrl), imageUrl);
            parts.add(new Part(inlineData, null, null));
        } else {
            FileData fileData = new FileData(getMimeTypeFromUrl(imageUrl), imageUrl);
            parts.add(new Part(null, fileData, null));
        }

        TextContent textContent = new TextContent(null, prompt);
        parts.add(new Part(null, null, textContent));

        return new VisionContentRecord("user", parts);
    }

    private DefaultVisionRequestPayloadRecord getAnthropicVisionContentRecord(TextGenerationConnection connection,
                                                                              String prompt, String imageUrl) throws IOException {
        List<Content> contentArray = new ArrayList<>();

        contentArray.add(new TextContent("text", prompt));

        // Add image content
        ImageSource imageSource;
        if (isBase64String(imageUrl)) {
            imageSource = new ImageSource("base64", getMimeType(imageUrl), imageUrl, null);
        } else {
            imageSource = new ImageSource("url", null, null, imageUrl);
        }
        contentArray.add(new ImageContent("image", imageSource));

        Message message = new Message("user", contentArray);

        return new DefaultVisionRequestPayloadRecord(connection.getModelName(),
                List.of(message),
                connection.getMaxTokens(),
                connection.getTemperature(),
                connection.getTopP());
    }

    private VisionRequestPayloadDTO buildPayload(TextGenerationConnection connection, List<Object> messagesArray) {

        String provider = ProviderUtils.getProviderByModel(connection.getModelName());

        return switch (provider) {
            case GOOGLE_PROVIDER_TYPE -> new VertexAIGooglePayloadRecord<>(messagesArray,
                    null,
                    buildVertexAIGoogleGenerationConfig(connection),
                    null,
                    null);
            case ANTHROPIC_PROVIDER_TYPE -> new VertexAIAnthropicPayloadRecord<>(VERTEX_AI_ANTHROPIC_VERSION_VALUE,
                    messagesArray,
                    connection.getMaxTokens(),
                    connection.getTemperature(),
                    connection.getTopP(),
                    null);
            case META_PROVIDER_TYPE -> new VertexAIMetaPayloadRecord<>(VERTEX_AI_ANTHROPIC_VERSION_VALUE,
                    messagesArray,
                    connection.getMaxTokens(),
                    connection.getTemperature(),
                    connection.getTopP(),
                    false);
            default -> getDefaultVisionRequestPayloadDTO(connection,messagesArray);
        };
    }

    private VertexAIAnthropicPayloadRecord<ChatPayloadRecord> getAnthropicRequestPayloadDTO(TextGenerationConnection connection, String prompt, String system) {

        VertexAIAnthropicChatPayloadRecord vertexAIAnthropicChatPayloadRecord = new VertexAIAnthropicChatPayloadRecord("text", prompt);

        ChatPayloadRecord payloadDTO;
        try {
            payloadDTO = new ChatPayloadRecord("user",
                    objectMapper.writeValueAsString(
                            List.of(vertexAIAnthropicChatPayloadRecord)));
        } catch (JsonProcessingException e) {
            throw new ModuleException("Error parsing JSON to VertexAIAnthropicChatPayloadRecord", InferenceErrorType.CHAT_COMPLETION_FAILURE);
        }
        return new VertexAIAnthropicPayloadRecord<>(VERTEX_AI_ANTHROPIC_VERSION_VALUE,
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

    private DefaultVisionRequestPayloadRecord getDefaultVisionRequestPayloadDTO(TextGenerationConnection connection, List<Object> chatPayloadRecordList) {
        return new DefaultVisionRequestPayloadRecord(connection.getModelName(),
                chatPayloadRecordList,
                connection.getMaxTokens(),
                connection.getTemperature(),
                connection.getTopP());
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
    private String getMimeTypeFromUrl(String imageUrl) {
        return imageUrl != null && imageUrl.toLowerCase().trim().endsWith(".png")
                ? "image/png"
                : DEFAULT_MIME_TYPE;
    }
}
