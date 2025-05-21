package com.mulesoft.connectors.inference.internal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.api.metadata.ImageResponseAttributes;
import com.mulesoft.connectors.inference.api.response.ImageGenerationResponse;
import com.mulesoft.connectors.inference.internal.connection.ImageGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.imagegeneration.ImageGenerationRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.imagegeneration.response.ImageGenerationRestResponse;
import com.mulesoft.connectors.inference.internal.helpers.McpHelper;
import com.mulesoft.connectors.inference.internal.helpers.ResponseHelper;
import com.mulesoft.connectors.inference.internal.helpers.payload.RequestPayloadHelper;
import com.mulesoft.connectors.inference.internal.helpers.response.HttpResponseHandler;
import com.mulesoft.connectors.inference.internal.utils.ConnectionUtils;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.TimeoutException;

public class ImageGenerationService implements BaseService{

    private static final Logger logger = LoggerFactory.getLogger(ImageGenerationService.class);
    public static final String PAYLOAD_LOGGER_MSG = "Payload sent to the LLM {}";

    private final RequestPayloadHelper payloadHelper;
    private final HttpResponseHandler responseHandler;
    private final McpHelper mcpHelper;
    private final ObjectMapper objectMapper;

    public ImageGenerationService(RequestPayloadHelper requestPayloadHelper, HttpResponseHandler responseHandler, McpHelper mcpHelper, ObjectMapper objectMapper) {
        this.payloadHelper = requestPayloadHelper;
        this.responseHandler = responseHandler;
        this.mcpHelper = mcpHelper;
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

        logger.debug("Request payload: {} ", requestPayloadDTO.toString());

        var response = ConnectionUtils.executeImageGenerationRestRequest(connection,
                connection.getApiURL(), requestPayloadDTO);

        ImageGenerationRestResponse imageGenerationRestResponse = responseHandler.processImageGenerationResponse(response);
        logger.debug("Response of image generation REST request: {}", imageGenerationRestResponse.toString());
        return imageGenerationRestResponse;
    }
}
