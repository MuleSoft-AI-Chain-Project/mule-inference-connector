package com.mulesoft.connectors.inference.internal.helpers.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.dto.imagegeneration.HugginFaceImageRequestPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.imagegeneration.ImageGenerationRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.imagegeneration.response.ImageData;
import com.mulesoft.connectors.inference.internal.dto.imagegeneration.response.ImageGenerationRestResponse;
import com.mulesoft.connectors.inference.internal.exception.InferenceErrorType;
import org.apache.commons.lang3.StringUtils;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

public class HuggingFaceHttpResponseHandler extends HttpResponseHandler{

    private static final Logger logger = LoggerFactory.getLogger(HuggingFaceHttpResponseHandler.class);

    public HuggingFaceHttpResponseHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public ImageGenerationRestResponse processImageGenerationResponse(ImageGenerationRequestPayloadDTO requestPayloadDTO, HttpResponse response) throws IOException {
        int statusCode = response.getStatusCode();

        if (statusCode == 200) {
            if(StringUtils.isNotBlank(response.getHeaderValue("Content-Type")) &&
                    response.getHeaderValue("Content-Type").startsWith("image/")) {

                String base64Image = encodeImageToBase64(response.getEntity().getBytes());

                HugginFaceImageRequestPayloadRecord payload= (HugginFaceImageRequestPayloadRecord)requestPayloadDTO;

                return new ImageGenerationRestResponse(null, List.of(new ImageData(base64Image, payload.inputs())));
            }
            logger.debug("Response is not an image.");
        }
        throw handleErrorResponse(response, statusCode,InferenceErrorType.IMAGE_GENERATION_FAILURE);
    }

    private String encodeImageToBase64(byte[] imageBytes) {
        return Base64.getEncoder().encodeToString(imageBytes);
    }
}
