package com.mulesoft.connectors.inference.internal.helpers.payload;

import org.mule.runtime.extension.api.exception.ModuleException;

import com.mulesoft.connectors.inference.api.request.ChatPayloadRecord;
import com.mulesoft.connectors.inference.api.request.FunctionDefinitionRecord;
import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.connection.types.VisionModelConnection;
import com.mulesoft.connectors.inference.internal.connection.types.vertexai.VertexAITextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.DefaultRequestPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.TextGenerationRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.VertexAIAnthropicChatPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.anthropic.VertexAIAnthropicPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.google.ContentRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.google.PartRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.google.SystemInstructionRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.google.VertexAIGoogleGenerationConfigRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.google.VertexAIGooglePayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.meta.VertexAIMetaPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.vision.Content;
import com.mulesoft.connectors.inference.internal.dto.vision.DefaultVisionRequestPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.vision.ImageContent;
import com.mulesoft.connectors.inference.internal.dto.vision.ImageSource;
import com.mulesoft.connectors.inference.internal.dto.vision.Message;
import com.mulesoft.connectors.inference.internal.dto.vision.TextContent;
import com.mulesoft.connectors.inference.internal.dto.vision.VisionRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.vision.vertexai.FileData;
import com.mulesoft.connectors.inference.internal.dto.vision.vertexai.InlineData;
import com.mulesoft.connectors.inference.internal.dto.vision.vertexai.Part;
import com.mulesoft.connectors.inference.internal.dto.vision.vertexai.VisionContentRecord;
import com.mulesoft.connectors.inference.internal.error.InferenceErrorType;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VertexAIRequestPayloadHelper extends RequestPayloadHelper {

  private static final Logger logger = LoggerFactory.getLogger(VertexAIRequestPayloadHelper.class);

  public static final String GOOGLE_PROVIDER_TYPE = "Google";
  public static final String ANTHROPIC_PROVIDER_TYPE = "Anthropic";
  public static final String META_PROVIDER_TYPE = "Meta";

  public static final String VERTEX_AI_ANTHROPIC_VERSION_VALUE = "vertex-2023-10-16";
  private static final String DEFAULT_MIME_TYPE = "image/jpeg";

  public VertexAIRequestPayloadHelper(ObjectMapper objectMapper) {
    super(objectMapper);
  }

  @Override
    public TextGenerationRequestPayloadDTO buildChatAnswerPromptPayload(TextGenerationConnection connection, String prompt) {

        String provider = getProviderByModel(connection.getModelName());

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
    public TextGenerationRequestPayloadDTO buildPromptTemplatePayload(TextGenerationConnection connection, String template, String instructions, String data) {

        String provider = getProviderByModel(connection.getModelName());

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
                         template + " - " + instructions, data);

                yield buildPayload(connection, messagesArray,null);
            }
        };
    }

  @Override
  public TextGenerationRequestPayloadDTO parseAndBuildChatCompletionPayload(TextGenerationConnection connection,
                                                                            InputStream messages)
          throws IOException {
      List<ContentRecord> messagesArray = objectMapper.readValue(
              messages,
              objectMapper.getTypeFactory()
                      .constructCollectionType(List.class, ContentRecord.class));

      String provider = getProviderByModel(connection.getModelName());

      return switch (provider) {
          case GOOGLE_PROVIDER_TYPE ->
                  new VertexAIGooglePayloadRecord<>(messagesArray,
                          null,
                          buildVertexAIGoogleGenerationConfig(connection.getMaxTokens(),connection.getTemperature(),connection.getTopP()),
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
          default -> throw new UnsupportedOperationException("Model not supported: " + connection.getModelName());
      };
  }

  @Override
  public TextGenerationRequestPayloadDTO buildToolsTemplatePayload(TextGenerationConnection connection, String template,
                                                                   String instructions, String data,
                                                                   List<FunctionDefinitionRecord> tools) {

    throw new UnsupportedOperationException("Currently not supported");
  }

  @Override
  public TextGenerationRequestPayloadDTO buildToolsTemplatePayload(TextGenerationConnection connection, String template,
                                                                   String instructions, String data, InputStream tools)
      throws IOException {
    String provider = getProviderByModel(connection.getModelName());

    throw new IllegalArgumentException(provider + ":" + connection.getModelName()
        + " on Vertex AI do not currently support function calling at this time.");
  }

  @Override
    public VisionRequestPayloadDTO createRequestImageURL(VisionModelConnection connection, String prompt, String imageUrl) throws IOException {

        String provider = getProviderByModel(connection.getModelName());

        Object content =  switch (provider) {
            case GOOGLE_PROVIDER_TYPE -> getGoogleVisionContentRecord(prompt, imageUrl);
            case ANTHROPIC_PROVIDER_TYPE -> getAnthropicVisionContentRecord(
                    connection,prompt,imageUrl);
            default -> throw new IllegalArgumentException("Unknown provider");
        };

        return buildVisionRequestPayload(connection, List.of(content));
    }

  public static String getProviderByModel(String modelName) {
    logger.debug("model name {}", modelName);

    if (modelName == null || modelName.isEmpty()) {
      return "Unknown";
    }
    String upperName = modelName.toUpperCase();

    if (upperName.startsWith("GEMINI")) {
      return GOOGLE_PROVIDER_TYPE;
    } else if (upperName.startsWith("CLAUDE")) {
      return ANTHROPIC_PROVIDER_TYPE;
    } else if (upperName.startsWith("META")) {
      return META_PROVIDER_TYPE;
    } else {
      return "Unknown";
    }
  }

  // get access token from google service acc key file
  public static String getAccessTokenFromServiceAccountKey(TextGenerationConnection connection) {

    try {
      VertexAITextGenerationConnection textGenerationConnection = (VertexAITextGenerationConnection) connection;
      FileInputStream serviceAccountStream = new FileInputStream(textGenerationConnection.getVertexAIServiceAccountKey());
      GoogleCredentials credentials = GoogleCredentials
          .fromStream(serviceAccountStream)
          .createScoped(Collections.singletonList("https://www.googleapis.com/auth/cloud-platform"));

      credentials.refreshIfExpired();
      String token = credentials.getAccessToken().getTokenValue();
      logger.debug("gcp access token {}", token);
      return token;
    } catch (IOException e) {
      throw new ModuleException("Error fetching the token for ibm watson.", InferenceErrorType.INVALID_CONNECTION, e);
    }
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

    parts.add(new Part(null, null, prompt));

    return new VisionContentRecord("user", parts);
  }

  private DefaultVisionRequestPayloadRecord getAnthropicVisionContentRecord(VisionModelConnection connection,
                                                                            String prompt, String imageUrl)
      throws IOException {
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

  private VisionRequestPayloadDTO buildVisionRequestPayload(VisionModelConnection connection, List<Object> messagesArray) {

        String provider = getProviderByModel(connection.getModelName());

        return switch (provider) {
            case GOOGLE_PROVIDER_TYPE -> new VertexAIGooglePayloadRecord<>(messagesArray,
                    null,
                    buildVertexAIGoogleGenerationConfig(connection.getMaxTokens(), connection.getTemperature(),connection.getTopP()),
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

  private VertexAIAnthropicPayloadRecord<ChatPayloadRecord> getAnthropicRequestPayloadDTO(TextGenerationConnection connection,
                                                                                          String prompt, String system) {

    VertexAIAnthropicChatPayloadRecord vertexAIAnthropicChatPayloadRecord =
        new VertexAIAnthropicChatPayloadRecord("text", prompt);

    ChatPayloadRecord payloadDTO;
    try {
      payloadDTO = new ChatPayloadRecord("user",
                                         objectMapper.writeValueAsString(
                                                                         List.of(vertexAIAnthropicChatPayloadRecord)));
    } catch (JsonProcessingException e) {
      throw new ModuleException("Error parsing JSON to VertexAIAnthropicChatPayloadRecord",
                                InferenceErrorType.CHAT_OPERATION_FAILURE);
    }
    return new VertexAIAnthropicPayloadRecord<>(VERTEX_AI_ANTHROPIC_VERSION_VALUE,
                                                List.of(payloadDTO),
                                                connection.getMaxTokens(),
                                                connection.getTemperature(),
                                                connection.getTopP(),
                                                system);
  }

  private DefaultRequestPayloadRecord getDefaultRequestPayloadDTO(TextGenerationConnection connection,
                                                                  List<ChatPayloadRecord> chatPayloadRecordList) {
    return new DefaultRequestPayloadRecord(connection.getModelName(),
                                           chatPayloadRecordList,
                                           connection.getMaxTokens(),
                                           connection.getTemperature(),
                                           connection.getTopP(), null);
  }

  private DefaultVisionRequestPayloadRecord getDefaultVisionRequestPayloadDTO(VisionModelConnection connection,
                                                                              List<Object> chatPayloadRecordList) {
    return new DefaultVisionRequestPayloadRecord(connection.getModelName(),
                                                 chatPayloadRecordList,
                                                 connection.getMaxTokens(),
                                                 connection.getTemperature(),
                                                 connection.getTopP());
  }

  private VertexAIGooglePayloadRecord buildVertexAIGooglePayload(TextGenerationConnection connection, String prompt,
                                                                 List<String> safetySettings,
                                                                 SystemInstructionRecord systemInstruction,
                                                                 List<FunctionDefinitionRecord> tools) {

    PartRecord partRecord = new PartRecord(prompt);
    ContentRecord contentRecord = new ContentRecord("user", List.of(partRecord));

    return new VertexAIGooglePayloadRecord<>(List.of(contentRecord),
                                             systemInstruction,
                                             buildVertexAIGoogleGenerationConfig(connection.getMaxTokens(),
                                                                                 connection.getTemperature(),
                                                                                 connection.getTopP()),
                                             safetySettings,
                                             tools != null && !tools.isEmpty() ? tools : null);
  }

  private VertexAIGoogleGenerationConfigRecord buildVertexAIGoogleGenerationConfig(Number maxTokens, Number temperature,
                                                                                   Number topP) {
    // create the generationConfig
    return new VertexAIGoogleGenerationConfigRecord(List.of("TEXT"), temperature,
                                                    topP, maxTokens);
  }

  private String getMimeTypeFromUrl(String imageUrl) {
    return imageUrl != null && imageUrl.toLowerCase().trim().endsWith(".png")
        ? "image/png"
        : DEFAULT_MIME_TYPE;
  }
}
