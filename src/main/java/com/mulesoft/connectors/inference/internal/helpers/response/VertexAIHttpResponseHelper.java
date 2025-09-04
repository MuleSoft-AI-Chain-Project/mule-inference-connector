package com.mulesoft.connectors.inference.internal.helpers.response;

import org.mule.runtime.http.api.domain.message.response.HttpResponse;

import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.gemini.GeminiChatCompletionResponse;
import com.mulesoft.connectors.inference.internal.error.InferenceErrorType;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VertexAIHttpResponseHelper extends HttpResponseHelper {

  private static final Logger logger = LoggerFactory.getLogger(VertexAIHttpResponseHelper.class);
  private final GeminiHttpResponseHelper geminiHttpResponseHelper;

  public VertexAIHttpResponseHelper(ObjectMapper objectMapper) {
    super(objectMapper);
    geminiHttpResponseHelper = new GeminiHttpResponseHelper(objectMapper);
  }

  @Override
  public GeminiChatCompletionResponse processChatResponse(HttpResponse response, InferenceErrorType errorType)
      throws IOException {

    logger.debug("Processing VertexAI chat response. Response Code:{}", response.getStatusCode());
    return geminiHttpResponseHelper.processChatResponse(response, errorType);
  }
}
