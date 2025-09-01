package com.mulesoft.connectors.inference.internal.helpers.response;

import org.mule.runtime.http.api.domain.message.response.HttpResponse;

import com.mulesoft.connectors.inference.internal.dto.textgeneration.gemini.FunctionCall;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.gemini.PartRecord;
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

      // Extract the first candidate's tool call (if it exists)
      if (geminiResponse.candidates() != null &&
          !geminiResponse.candidates().isEmpty() &&
          geminiResponse.candidates().get(0).content() != null &&
          geminiResponse.candidates().get(0).content().parts() != null &&
          !geminiResponse.candidates().get(0).content().parts().isEmpty()) {

        PartRecord part = geminiResponse.candidates().get(0).content().parts().get(0);

        if (part.functionCall() != null) {
          FunctionCall toolCall = part.functionCall();
          logger.debug("Tool call detected: name={}, args={}", toolCall.name(), toolCall.args());
        }
      }

      return geminiResponse;
    }
    throw handleErrorResponse(response, statusCode, errorType);
  }
}
