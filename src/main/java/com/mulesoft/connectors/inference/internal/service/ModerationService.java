package com.mulesoft.connectors.inference.internal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.ImageGenerationConnection;
import com.mulesoft.connectors.inference.internal.connection.ModerationConnection;
import com.mulesoft.connectors.inference.internal.constants.InferenceConstants;
import com.mulesoft.connectors.inference.internal.dto.imagegeneration.ImageGenerationRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.imagegeneration.response.ImageGenerationRestResponse;
import com.mulesoft.connectors.inference.internal.dto.moderation.ModerationRequestPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.moderation.response.ModerationRestResponse;
import com.mulesoft.connectors.inference.internal.helpers.ResponseHelper;
import com.mulesoft.connectors.inference.internal.helpers.payload.RequestPayloadHelper;
import com.mulesoft.connectors.inference.internal.helpers.request.HttpRequestHandler;
import com.mulesoft.connectors.inference.internal.helpers.response.HttpResponseHandler;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeoutException;

public class ModerationService implements BaseService{

    private static final Logger logger = LoggerFactory.getLogger(ModerationService.class);

    private final RequestPayloadHelper payloadHelper;
    private final HttpRequestHandler httpRequestHandler;
    private final HttpResponseHandler responseHandler;
    private final ObjectMapper objectMapper;

    public ModerationService(RequestPayloadHelper requestPayloadHelper, HttpRequestHandler httpRequestHandler,
                             HttpResponseHandler responseHandler, ObjectMapper objectMapper) {
        this.payloadHelper = requestPayloadHelper;
        this.httpRequestHandler = httpRequestHandler;
        this.responseHandler = responseHandler;
        this.objectMapper = objectMapper;
    }

    public Result<InputStream, Void> executeTextModeration(ModerationConnection connection, InputStream text) throws IOException, TimeoutException {
        ModerationRequestPayloadRecord payload = payloadHelper.getModerationRequestPayload(connection.getModelName(),text);
        logger.debug("Moderation payload that will be sent to the LLM {}", payload);

        var response = httpRequestHandler.executeModerationRestRequest(connection,connection.getApiURL(),payload);
        logger.debug("Moderation service - response from LLM: {}", response);

        ModerationRestResponse moderationRestResponse = responseHandler.processModerationResponse(response);
        ;
        return ResponseHelper.createLLMResponse(
                objectMapper.writeValueAsString(responseHandler.mapModerationFinalResponse(moderationRestResponse)));
    }
}
