package com.mulesoft.connectors.inference.internal.service;

import org.mule.runtime.extension.api.runtime.operation.Result;

import com.mulesoft.connectors.inference.api.metadata.AdditionalAttributes;
import com.mulesoft.connectors.inference.api.metadata.LLMResponseAttributes;
import com.mulesoft.connectors.inference.api.response.TextGenerationResponse;
import com.mulesoft.connectors.inference.internal.connection.types.VisionModelConnection;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.ChatCompletionResponse;
import com.mulesoft.connectors.inference.internal.dto.vision.VisionRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.error.InferenceErrorType;
import com.mulesoft.connectors.inference.internal.helpers.ResponseHelper;
import com.mulesoft.connectors.inference.internal.helpers.TokenHelper;
import com.mulesoft.connectors.inference.internal.helpers.payload.RequestPayloadHelper;
import com.mulesoft.connectors.inference.internal.helpers.request.HttpRequestHelper;
import com.mulesoft.connectors.inference.internal.helpers.response.HttpResponseHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VisionModelService implements BaseService {

  private static final Logger logger = LoggerFactory.getLogger(VisionModelService.class);

  private final RequestPayloadHelper payloadHelper;
  private final HttpRequestHelper httpRequestHelper;
  private final HttpResponseHelper responseHandler;
  private final ObjectMapper objectMapper;

  public VisionModelService(RequestPayloadHelper requestPayloadHelper, HttpRequestHelper httpRequestHelper,
                            HttpResponseHelper responseHandler, ObjectMapper objectMapper) {
    this.payloadHelper = requestPayloadHelper;
    this.httpRequestHelper = httpRequestHelper;
    this.responseHandler = responseHandler;
    this.objectMapper = objectMapper;
  }

  public Result<InputStream, LLMResponseAttributes> readImage(VisionModelConnection connection, String prompt, String imageUrl)
      throws IOException, TimeoutException {

    VisionRequestPayloadDTO visionPayload = payloadHelper.createRequestImageURL(connection, prompt, imageUrl);

    logger.debug("payload sent to the LLM {}", visionPayload);

    var response = httpRequestHelper.executeVisionRestRequest(connection, connection.getApiURL(), visionPayload);

    ChatCompletionResponse chatResponse =
        responseHandler.processChatResponse(response, InferenceErrorType.READ_IMAGE_OPERATION_FAILURE);
    logger.debug("Response of vision REST request: {}", chatResponse);

    var chatRespFirstChoice = chatResponse.choices().get(0);

    return ResponseHelper.createLLMResponse(
                                            objectMapper.writeValueAsString(new TextGenerationResponse(
                                                                                                       chatRespFirstChoice
                                                                                                           .message().content(),
                                                                                                       chatRespFirstChoice
                                                                                                           .message().toolCalls(),
                                                                                                       null)),
                                            TokenHelper.parseUsageFromResponse(chatResponse.usage()),
                                            new AdditionalAttributes(chatResponse.id(), chatResponse.model(),
                                                                     chatRespFirstChoice.finishReason()));
  }
}
