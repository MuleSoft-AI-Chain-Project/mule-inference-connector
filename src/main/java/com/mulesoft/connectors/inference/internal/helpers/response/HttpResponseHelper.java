package com.mulesoft.connectors.inference.internal.helpers.response;

import org.mule.runtime.extension.api.exception.ModuleException;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;

import com.mulesoft.connectors.inference.api.response.ModerationResponse;
import com.mulesoft.connectors.inference.internal.dto.imagegeneration.ImageGenerationRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.imagegeneration.response.ImageGenerationRestResponse;
import com.mulesoft.connectors.inference.internal.dto.moderation.response.ModerationRestResponse;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.ChatCompletionResponse;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.TextResponseDTO;
import com.mulesoft.connectors.inference.internal.error.InferenceErrorType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponseHelper {

  private static final Logger logger = LoggerFactory.getLogger(HttpResponseHelper.class);

  protected final ObjectMapper objectMapper;

  public HttpResponseHelper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public TextResponseDTO processChatResponse(HttpResponse response, InferenceErrorType errorType) throws IOException {
    int statusCode = response.getStatusCode();

    if (statusCode == 200) {
      return objectMapper.readValue(response.getEntity().getBytes(), ChatCompletionResponse.class);
    }
    throw handleErrorResponse(response, statusCode, errorType);
  }

  public ImageGenerationRestResponse processImageGenerationResponse(ImageGenerationRequestPayloadDTO requestPayloadDTO,
                                                                    HttpResponse response)
      throws IOException {
    logger.debug("Image generation requestPayload:{}", requestPayloadDTO);

    int statusCode = response.getStatusCode();

    if (statusCode == 200) {
      return objectMapper.readValue(response.getEntity().getBytes(), ImageGenerationRestResponse.class);
    }
    throw handleErrorResponse(response, statusCode, InferenceErrorType.IMAGE_GENERATION_FAILURE);
  }

  public ModerationRestResponse processModerationResponse(HttpResponse response) throws IOException {
    int statusCode = response.getStatusCode();

    if (statusCode == 200) {
      return objectMapper.readValue(response.getEntity().getBytes(), ModerationRestResponse.class);
    }
    throw handleErrorResponse(response, statusCode, InferenceErrorType.IMAGE_GENERATION_FAILURE);
  }

  public ModerationResponse mapModerationFinalResponse(ModerationRestResponse moderationRestResponse) {

    return new ModerationResponse(isResponseFlagged(moderationRestResponse), getCategories(moderationRestResponse));
  }

  protected ModuleException handleErrorResponse(HttpResponse response, int statusCode, InferenceErrorType errorType)
      throws IOException {
    String errorResponse = new String(response.getEntity().getBytes(), StandardCharsets.UTF_8);
    logger.error("API request failed with status code: {} and message: {}", statusCode, errorResponse);
    if (statusCode == HttpStatus.SC_TOO_MANY_REQUESTS)
      return new ModuleException(getFormattedErrorMessage(statusCode, errorResponse),
                                 InferenceErrorType.RATE_LIMIT_EXCEEDED);
    return new ModuleException(getFormattedErrorMessage(statusCode, errorResponse), errorType);
  }

  private boolean isResponseFlagged(ModerationRestResponse moderationRestResponse) {
    return moderationRestResponse.results().stream()
        .anyMatch(result -> result.categories().entrySet().stream()
            .anyMatch(Map.Entry::getValue));
  }

  private List<Map<String, Double>> getCategories(ModerationRestResponse response) {
    return response.results().stream()
        .map(result -> result.categories().keySet().stream()
            .collect(Collectors.toMap(
                                      key -> key,
                                      key -> result.categoryScores().get(key))))
        .toList();
  }

  private String getFormattedErrorMessage(int statusCode, String errorResponse) {
    return "Request failed with status code: " + statusCode + " and message: " + errorResponse;
  }
}
