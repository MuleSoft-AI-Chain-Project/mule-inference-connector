package com.mulesoft.connectors.inference.internal.helpers.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.dto.imagegeneration.ImageGenerationRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.imagegeneration.response.ImageGenerationRestResponse;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.ChatCompletionResponse;
import com.mulesoft.connectors.inference.internal.exception.InferenceErrorType;
import org.mule.runtime.extension.api.exception.ModuleException;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HttpResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponseHandler.class);

    protected final ObjectMapper objectMapper;

    public HttpResponseHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ChatCompletionResponse processChatResponse(HttpResponse response) throws IOException {
        int statusCode = response.getStatusCode();

        if (statusCode == 200) {
            return objectMapper.readValue(response.getEntity().getBytes(), ChatCompletionResponse.class);
        }
        throw handleErrorResponse(response, statusCode, InferenceErrorType.CHAT_OPERATION_FAILURE);
    }

    public ImageGenerationRestResponse processImageGenerationResponse(ImageGenerationRequestPayloadDTO requestPayloadDTO,
                                                                      HttpResponse response) throws IOException {
        int statusCode = response.getStatusCode();

        if (statusCode == 200) {
            return objectMapper.readValue(response.getEntity().getBytes(), ImageGenerationRestResponse.class);
        }
        throw handleErrorResponse(response, statusCode,InferenceErrorType.IMAGE_GENERATION_FAILURE);
    }

    protected ModuleException handleErrorResponse(HttpResponse response, int statusCode, InferenceErrorType errorType) throws IOException {
        String errorResponse = new String(response.getEntity().getBytes(), StandardCharsets.UTF_8);
        logger.error("API request failed with status code: {} and message: {}", statusCode, errorResponse);
        return new ModuleException(getFormattedErrorMessage(statusCode, errorResponse), errorType);
    }

    private String getFormattedErrorMessage(int statusCode, String errorResponse) {
        return "Chat request failed with status code: " + statusCode + " and message: " + errorResponse;
    }
}
