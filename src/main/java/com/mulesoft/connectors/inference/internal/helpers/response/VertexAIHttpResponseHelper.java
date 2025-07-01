package com.mulesoft.connectors.inference.internal.helpers.response;

import org.mule.runtime.http.api.domain.message.response.HttpResponse;

import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.vertexai.VertexAiChatCompletionResponse;
import com.mulesoft.connectors.inference.internal.error.InferenceErrorType;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VertexAIHttpResponseHelper extends HttpResponseHelper {

  private static final Logger logger = LoggerFactory.getLogger(VertexAIHttpResponseHelper.class);

  public VertexAIHttpResponseHelper(ObjectMapper objectMapper) {
    super(objectMapper);
  }

  @Override
  public VertexAiChatCompletionResponse processChatResponse(HttpResponse response, InferenceErrorType errorType)
      throws IOException {

    logger.debug("Processing VertexAI chat response. Response Code:{}", response.getStatusCode());
    int statusCode = response.getStatusCode();

    if (statusCode == 200) {
      return objectMapper.readValue(response.getEntity().getBytes(), VertexAiChatCompletionResponse.class);
    }
    throw handleErrorResponse(response, statusCode, errorType);
  }
}
