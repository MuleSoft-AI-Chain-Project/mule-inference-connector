package com.mulesoft.connectors.inference.internal.helpers.payload;

import com.mulesoft.connectors.inference.api.request.ChatPayloadRecord;
import com.mulesoft.connectors.inference.api.request.FunctionDefinitionRecord;
import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.connection.types.VisionModelConnection;
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
                                                                   String instructions, String data, InputStream tools)
      throws IOException {

    List<FunctionDefinitionRecord> toolsRecord = parseInputStreamToTools(tools);

    logger.debug("toolsArray: {}", toolsRecord);

    List<ChatPayloadRecord> messagesArray = createMessagesArrayWithSystemPrompt(
                                                                                connection, template + " - " + instructions,
                                                                                data);

    return buildPayload(connection, messagesArray, toolsRecord);
  }

  @Override
  public DefaultVisionRequestPayloadRecord createRequestImageURL(VisionModelConnection connection, String prompt, String imageUrl)
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
}
