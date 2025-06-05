package com.mulesoft.connectors.inference.internal.service;

import org.mule.runtime.extension.api.runtime.operation.Result;

import com.mulesoft.connectors.inference.internal.connection.types.ModerationConnection;
import com.mulesoft.connectors.inference.internal.dto.moderation.ModerationRequestPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.moderation.response.ModerationRestResponse;
import com.mulesoft.connectors.inference.internal.helpers.ResponseHelper;
import com.mulesoft.connectors.inference.internal.helpers.payload.RequestPayloadHelper;
import com.mulesoft.connectors.inference.internal.helpers.request.HttpRequestHelper;
import com.mulesoft.connectors.inference.internal.helpers.response.HttpResponseHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModerationService implements BaseService {

  private static final Logger logger = LoggerFactory.getLogger(ModerationService.class);

  private final RequestPayloadHelper payloadHelper;
  private final HttpRequestHelper httpRequestHelper;
  private final HttpResponseHelper responseHelper;
  private final ObjectMapper objectMapper;

  public ModerationService(RequestPayloadHelper requestPayloadHelper, HttpRequestHelper httpRequestHelper,
                           HttpResponseHelper responseHelper, ObjectMapper objectMapper) {
    this.payloadHelper = requestPayloadHelper;
    this.httpRequestHelper = httpRequestHelper;
    this.responseHelper = responseHelper;
    this.objectMapper = objectMapper;
  }

  public Result<InputStream, Void> executeTextModeration(ModerationConnection connection, InputStream text)
      throws IOException, TimeoutException {
    ModerationRequestPayloadRecord payload = payloadHelper.getModerationRequestPayload(connection.getModelName(), text);
    logger.debug("Moderation payload that will be sent to the LLM {}", payload);

    var response = httpRequestHelper.executeModerationRestRequest(connection, connection.getApiURL(), payload);
    logger.debug("Moderation service - response from LLM: {}", response);

    ModerationRestResponse moderationRestResponse = responseHelper.processModerationResponse(response);
    return ResponseHelper.createLLMResponse(
                                            objectMapper.writeValueAsString(responseHelper
                                                .mapModerationFinalResponse(moderationRestResponse)));
  }
}
