package com.mulesoft.connectors.inference.internal.helpers.payload;

import com.mulesoft.connectors.inference.api.request.ChatPayloadRecord;
import com.mulesoft.connectors.inference.api.request.FunctionDefinitionRecord;
import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.connection.types.VisionModelConnection;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.DefaultRequestPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.TextGenerationRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.google.ContentRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.google.PartRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.google.SystemInstructionRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.google.VertexAIGoogleGenerationConfigRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.google.VertexAIGooglePayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.vision.DefaultVisionRequestPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.vision.VisionRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.vision.vertexai.FileData;
import com.mulesoft.connectors.inference.internal.dto.vision.vertexai.InlineData;
import com.mulesoft.connectors.inference.internal.dto.vision.vertexai.Part;
import com.mulesoft.connectors.inference.internal.dto.vision.vertexai.VisionContentRecord;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    } else {
      return "Unknown";
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

  private VisionRequestPayloadDTO buildVisionRequestPayload(VisionModelConnection connection, List<Object> messagesArray) {

        String provider = getProviderByModel(connection.getModelName());

        return switch (provider) {
            case GOOGLE_PROVIDER_TYPE -> new VertexAIGooglePayloadRecord<>(messagesArray,
                    null,
                    buildVertexAIGoogleGenerationConfig(connection.getMaxTokens(), connection.getTemperature(),connection.getTopP()),
                    null,
                    null);
            default -> getDefaultVisionRequestPayloadDTO(connection,messagesArray);
        };
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

  private VertexAIGooglePayloadRecord<ContentRecord> buildVertexAIGooglePayload(TextGenerationConnection connection,
                                                                                String prompt,
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
    if(imageUrl==null||imageUrl.isBlank()){return DEFAULT_MIME_TYPE;}

    String trimmedUrl=imageUrl.trim();int lastDotIndex=trimmedUrl.lastIndexOf('.');

    if(lastDotIndex==-1){return DEFAULT_MIME_TYPE;}

    String extension=trimmedUrl.substring(lastDotIndex).toLowerCase();

    return switch(extension){case".png"->"image/png";case".pdf"->"application/pdf";default->DEFAULT_MIME_TYPE;};
  }

}
