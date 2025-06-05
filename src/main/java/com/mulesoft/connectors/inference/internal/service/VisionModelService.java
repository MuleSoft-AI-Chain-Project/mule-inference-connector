package com.mulesoft.connectors.inference.internal.service;

import org.mule.runtime.extension.api.runtime.operation.Result;

import com.mulesoft.connectors.inference.api.metadata.LLMResponseAttributes;
import com.mulesoft.connectors.inference.internal.connection.types.VisionModelConnection;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.TextResponseDTO;
import com.mulesoft.connectors.inference.internal.dto.vision.VisionRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.error.InferenceErrorType;
import com.mulesoft.connectors.inference.internal.helpers.ResponseHelper;
import com.mulesoft.connectors.inference.internal.helpers.payload.RequestPayloadHelper;
import com.mulesoft.connectors.inference.internal.helpers.request.HttpRequestHelper;
import com.mulesoft.connectors.inference.internal.helpers.response.HttpResponseHelper;
import com.mulesoft.connectors.inference.internal.helpers.response.mapper.DefaultResponseMapper;

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
  private final HttpResponseHelper responseHelper;
  private final DefaultResponseMapper responseParser;
  private final ObjectMapper objectMapper;

  public VisionModelService(RequestPayloadHelper requestPayloadHelper, HttpRequestHelper httpRequestHelper,
                            HttpResponseHelper responseHelper, DefaultResponseMapper responseParser, ObjectMapper objectMapper) {
    this.payloadHelper = requestPayloadHelper;
    this.httpRequestHelper = httpRequestHelper;
    this.responseHelper = responseHelper;
    this.responseParser = responseParser;
    this.objectMapper = objectMapper;
  }

  public Result<InputStream, LLMResponseAttributes> readImage(VisionModelConnection connection, String prompt, String imageUrl)
      throws IOException, TimeoutException {

    VisionRequestPayloadDTO visionPayload = payloadHelper.createRequestImageURL(connection, prompt, imageUrl);

    logger.debug("payload sent to the LLM {}", visionPayload);

    var response = httpRequestHelper.executeVisionRestRequest(connection, connection.getApiURL(), visionPayload);

    TextResponseDTO chatResponse =
        responseHelper.processChatResponse(response, InferenceErrorType.READ_IMAGE_OPERATION_FAILURE);
    logger.debug("Response of vision REST request: {}", chatResponse);

    return ResponseHelper.createLLMResponse(objectMapper.writeValueAsString(
                                                                            responseParser.mapChatResponse(chatResponse)),
                                            responseParser.mapTokenUsageFromResponse(chatResponse),
                                            responseParser.mapAdditionalAttributes(chatResponse));
  }
}
