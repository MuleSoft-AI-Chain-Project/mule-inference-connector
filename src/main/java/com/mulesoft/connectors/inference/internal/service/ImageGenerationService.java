package com.mulesoft.connectors.inference.internal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.api.metadata.ImageResponseAttributes;
import com.mulesoft.connectors.inference.api.response.ImageGenerationResponse;
import com.mulesoft.connectors.inference.internal.connection.ImageGenerationConnection;
import com.mulesoft.connectors.inference.internal.constants.InferenceConstants;
import com.mulesoft.connectors.inference.internal.dto.imagegeneration.ImageGenerationRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.imagegeneration.response.ImageGenerationRestResponse;
import com.mulesoft.connectors.inference.internal.helpers.ResponseHelper;
import com.mulesoft.connectors.inference.internal.helpers.payload.RequestPayloadHelper;
import com.mulesoft.connectors.inference.internal.helpers.request.HttpRequestHandler;
import com.mulesoft.connectors.inference.internal.helpers.response.HttpResponseHandler;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.TimeoutException;

public class ImageGenerationService implements BaseService{

    private static final Logger logger = LoggerFactory.getLogger(ImageGenerationService.class);

    private final RequestPayloadHelper payloadHelper;
    private final HttpRequestHandler httpRequestHandler;
    private final HttpResponseHandler responseHandler;
    private final ObjectMapper objectMapper;

    public ImageGenerationService(RequestPayloadHelper requestPayloadHelper, HttpRequestHandler httpRequestHandler,
                                  HttpResponseHandler responseHandler, ObjectMapper objectMapper) {
        this.payloadHelper = requestPayloadHelper;
        this.httpRequestHandler = httpRequestHandler;
        this.responseHandler = responseHandler;
        this.objectMapper = objectMapper;
    }
    public Result<InputStream, ImageResponseAttributes> executeGenerateImage(ImageGenerationConnection connection, String prompt) throws IOException, TimeoutException {

        ImageGenerationRequestPayloadDTO requestPayloadDTO = payloadHelper
                .createRequestImageGeneration(connection.getModelName(), prompt);

        URL imageGenerationUrl = new URL(connection.getApiURL());
        logger.debug("Generate Image with {}", imageGenerationUrl);

        var response = executeImageGenerationRequest(connection,requestPayloadDTO);

        return ResponseHelper.createImageGenerationLLMResponse(
                objectMapper.writeValueAsString(new ImageGenerationResponse(response.data().get(0).b64Json())),
                connection.getModelName(),
                response.data().get(0).revisedPrompt());
    }

    private ImageGenerationRestResponse executeImageGenerationRequest(ImageGenerationConnection connection,
                                                                      ImageGenerationRequestPayloadDTO requestPayloadDTO)
            throws IOException, TimeoutException {

        logger.debug(InferenceConstants.PAYLOAD_LOGGER_MSG, requestPayloadDTO.toString());

        var response = httpRequestHandler.executeImageGenerationRestRequest(connection,
                connection.getApiURL(), requestPayloadDTO);

        logger.debug("Image Generation Response Status code:{} ", response.getStatusCode());
        logger.trace("Image Generation Response headers:{} ", response.getHeaders().toString());
        logger.trace("Image Generation Response Entity: " + response.getEntity());

        ImageGenerationRestResponse imageGenerationRestResponse = responseHandler.processImageGenerationResponse(requestPayloadDTO,response);
        logger.debug("Response of image generation REST request: {}", imageGenerationRestResponse.toString());
        return imageGenerationRestResponse;
    }
}
