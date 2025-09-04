package com.mulesoft.connectors.inference.internal.helpers.response;

import org.mule.runtime.http.api.domain.message.response.HttpResponse;

import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.gemini.GeminiChatCompletionResponse;
import com.mulesoft.connectors.inference.internal.error.InferenceErrorType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeminiHttpResponseHelper extends HttpResponseHelper {

  private static final Logger logger = LoggerFactory.getLogger(GeminiHttpResponseHelper.class);

  public GeminiHttpResponseHelper(ObjectMapper objectMapper) {
    super(objectMapper);
  }

  @Override
  public GeminiChatCompletionResponse processChatResponse(HttpResponse response, InferenceErrorType errorType)
      throws IOException {

    logger.debug("Processing Gemini chat response. Response Code:{}", response.getStatusCode());
    int statusCode = response.getStatusCode();

    if (statusCode == 200) {
      String responseBody = new String(response.getEntity().getBytes(), StandardCharsets.UTF_8);
      logger.debug("Gemini raw JSON response:\n{}", responseBody);

      GeminiChatCompletionResponse geminiResponse =
          objectMapper.readValue(responseBody, GeminiChatCompletionResponse.class);

      logger.debug("Gemini response: {}", geminiResponse);

      return geminiResponse;
    }
    throw handleErrorResponse(response, statusCode, errorType);
  }
}
