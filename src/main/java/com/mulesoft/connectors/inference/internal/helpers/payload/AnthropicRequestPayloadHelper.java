package com.mulesoft.connectors.inference.internal.helpers.payload;

import com.mulesoft.connectors.inference.api.request.ChatPayloadRecord;
import com.mulesoft.connectors.inference.api.request.FunctionDefinitionRecord;
import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.connection.types.VisionModelConnection;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.AnthropicRequestPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.AnthropicToolCallRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.TextGenerationRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.vision.Content;
import com.mulesoft.connectors.inference.internal.dto.vision.DefaultVisionRequestPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.vision.ImageContent;
import com.mulesoft.connectors.inference.internal.dto.vision.ImageSource;
import com.mulesoft.connectors.inference.internal.dto.vision.Message;
import com.mulesoft.connectors.inference.internal.dto.vision.TextContent;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnthropicRequestPayloadHelper extends RequestPayloadHelper {

  private static final Logger logger = LoggerFactory.getLogger(AnthropicRequestPayloadHelper.class);

  public AnthropicRequestPayloadHelper(ObjectMapper objectMapper) {
    super(objectMapper);
  }

  @Override
  public TextGenerationRequestPayloadDTO buildToolsTemplatePayload(TextGenerationConnection connection, String template,
                                                                   String instructions, String data, InputStream tools,
                                                                   Map<String, Object> additionalRequestAttributes)
      throws IOException {

    List<FunctionDefinitionRecord> toolRecords = parseInputStreamToTools(tools);

    logger.debug("toolsArray: {}", toolRecords);

    List<ChatPayloadRecord> messages = createMessagesArrayWithSystemPrompt(
                                                                           template + " - " + instructions,
                                                                           data);

    return buildPayload(connection, messages, toolRecords, additionalRequestAttributes);
  }

  @Override
  public TextGenerationRequestPayloadDTO buildPayload(TextGenerationConnection connection, List<ChatPayloadRecord> messages,
                                                      List<FunctionDefinitionRecord> tools,
                                                      Map<String, Object> additionalRequestAttributes) {
    return new AnthropicRequestPayloadRecord(connection.getModelName(),
                                             messages,
                                             connection.getMaxTokens(),
                                             connection.getTemperature(),
                                             connection.getTopP(),
                                             getList(tools), additionalRequestAttributes);
  }

  private List<AnthropicToolCallRecord> getList(List<FunctionDefinitionRecord> tools) {
    return Optional.ofNullable(tools).map(list -> list.stream()
        .map(tool -> new AnthropicToolCallRecord(tool.function().name(),
                                                 tool.function().description(),
                                                 tool.function().parameters()))
        .toList()).orElse(null);
  }

  @Override
  protected List<ChatPayloadRecord> createMessagesArrayWithSystemPrompt(String systemContent,
                                                                        String userContent) {

    ChatPayloadRecord systemMessage = new ChatPayloadRecord("assistant",
                                                            systemContent);

    // Create user message
    ChatPayloadRecord userMessage = new ChatPayloadRecord("user", userContent);

    return List.of(systemMessage, userMessage);
  }

  @Override
  public DefaultVisionRequestPayloadRecord createRequestImageURL(VisionModelConnection connection, String prompt, String imageUrl,
                                                                 Map<String, Object> additionalRequestAttributes)
      throws IOException {
    List<Content> contents = new ArrayList<>();

    contents.add(new TextContent("text", prompt));

    // Add image content
    ImageSource imageSource;
    if (isBase64String(imageUrl)) {
      imageSource = new ImageSource("base64", getMimeType(imageUrl), imageUrl, null);
    } else {
      imageSource = new ImageSource("url", null, null, imageUrl);
    }
    contents.add(new ImageContent("image", imageSource));

    Message message = new Message("user", contents);

    return new DefaultVisionRequestPayloadRecord(connection.getModelName(),
                                                 List.of(message),
                                                 connection.getMaxTokens(),
                                                 connection.getTemperature(),
                                                 connection.getTopP(),
                                                 additionalRequestAttributes);
  }
}
