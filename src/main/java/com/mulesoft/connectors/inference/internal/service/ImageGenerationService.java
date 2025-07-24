package com.mulesoft.connectors.inference.internal.service;

import org.mule.runtime.extension.api.runtime.operation.Result;

import com.mulesoft.connectors.inference.api.metadata.ImageResponseAttributes;
import com.mulesoft.connectors.inference.api.response.ImageGenerationResponse;
import com.mulesoft.connectors.inference.internal.connection.types.ImageGenerationConnection;
import com.mulesoft.connectors.inference.internal.constants.InferenceConstants;
import com.mulesoft.connectors.inference.internal.dto.imagegeneration.ImageGenerationRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.imagegeneration.response.ImageGenerationRestResponse;
import com.mulesoft.connectors.inference.internal.helpers.ResponseHelper;
import com.mulesoft.connectors.inference.internal.helpers.payload.RequestPayloadHelper;
import com.mulesoft.connectors.inference.internal.helpers.request.HttpRequestHelper;
import com.mulesoft.connectors.inference.internal.helpers.response.HttpResponseHelper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageGenerationService implements BaseService {

  private static final Logger logger = LoggerFactory.getLogger(ImageGenerationService.class);

  private final RequestPayloadHelper payloadHelper;
  private final HttpRequestHelper httpRequestHelper;
  private final HttpResponseHelper responseHelper;
  private final ObjectMapper objectMapper;

  public ImageGenerationService(RequestPayloadHelper requestPayloadHelper, HttpRequestHelper httpRequestHelper,
                                HttpResponseHelper responseHelper, ObjectMapper objectMapper) {
    this.payloadHelper = requestPayloadHelper;
    this.httpRequestHelper = httpRequestHelper;
    this.responseHelper = responseHelper;
    this.objectMapper = objectMapper;
  }

  public Result<InputStream, ImageResponseAttributes> executeGenerateImage(ImageGenerationConnection connection, String prompt)
      throws IOException, TimeoutException {

    ImageGenerationRequestPayloadDTO requestPayloadDTO = payloadHelper
        .createRequestImageGeneration(connection.getModelName(), prompt);

    URL imageGenerationUrl = new URL(connection.getApiURL());
    logger.debug("Generate Image with {}", imageGenerationUrl);

    var response = executeImageGenerationRequest(connection, requestPayloadDTO);

    return ResponseHelper.createImageGenerationLLMResponse(
                                                           objectMapper.writeValueAsString(new ImageGenerationResponse(response
                                                               .data().get(0).b64Json())),
                                                           connection.getModelName(),
                                                           response.data().get(0).revisedPrompt());
  }

  private ImageGenerationRestResponse executeImageGenerationRequest(ImageGenerationConnection connection,
                                                                    ImageGenerationRequestPayloadDTO requestPayloadDTO)
      throws IOException, TimeoutException {

    logger.debug(InferenceConstants.PAYLOAD_LOGGER_MSG, requestPayloadDTO);
    var response = httpRequestHelper.executeImageGenerationRestRequest(connection,
                                                                       connection.getApiURL(), requestPayloadDTO);

    logger.debug("Image Generation Response Status code:{} ", response.getStatusCode());
    logger.trace("Image Generation Response headers:{} ", response.getHeaders());
    logger.trace("Image Generation Response Entity: {}", response.getEntity());

    ImageGenerationRestResponse imageGenerationRestResponse =
        responseHelper.processImageGenerationResponse(requestPayloadDTO, response);
    logger.debug("Response of image generation REST request: {}", imageGenerationRestResponse);
    return imageGenerationRestResponse;
  }
}
