package com.mulesoft.connectors.inference.internal.helpers.response;

import org.mule.runtime.http.api.domain.message.response.HttpResponse;

import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.cohere.CohereChatCompletionResponse;
import com.mulesoft.connectors.inference.internal.error.InferenceErrorType;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CohereHttpResponseHelper extends HttpResponseHelper {

  private static final Logger logger = LoggerFactory.getLogger(CohereHttpResponseHelper.class);

  public CohereHttpResponseHelper(ObjectMapper objectMapper) {
    super(objectMapper);
  }

  @Override
  public CohereChatCompletionResponse processChatResponse(HttpResponse response, InferenceErrorType errorType)
      throws IOException {

    logger.debug("Processing Anthropic chat response. Response Code:{}", response.getStatusCode());
    int statusCode = response.getStatusCode();

    if (statusCode == 200) {
      return objectMapper.readValue(response.getEntity().getBytes(), CohereChatCompletionResponse.class);
    }
    throw handleErrorResponse(response, statusCode, errorType);
  }
}
