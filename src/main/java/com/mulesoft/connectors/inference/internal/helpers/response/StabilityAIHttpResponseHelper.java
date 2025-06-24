package com.mulesoft.connectors.inference.internal.helpers.response;

import org.mule.runtime.http.api.domain.message.response.HttpResponse;

import com.mulesoft.connectors.inference.internal.constants.InferenceConstants;
import com.mulesoft.connectors.inference.internal.dto.imagegeneration.ImageGenerationRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.imagegeneration.StabilityAIImageRequestPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.imagegeneration.response.ImageData;
import com.mulesoft.connectors.inference.internal.dto.imagegeneration.response.ImageGenerationRestResponse;
import com.mulesoft.connectors.inference.internal.error.InferenceErrorType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StabilityAIHttpResponseHelper extends HttpResponseHelper {

  private static final Logger logger = LoggerFactory.getLogger(StabilityAIHttpResponseHelper.class);

  public StabilityAIHttpResponseHelper(ObjectMapper objectMapper) {
    super(objectMapper);
  }

  @Override
  public ImageGenerationRestResponse processImageGenerationResponse(ImageGenerationRequestPayloadDTO requestPayloadDTO,
                                                                    HttpResponse response)
      throws IOException {
    int statusCode = response.getStatusCode();
    String contentType = response.getHeaderValue(InferenceConstants.HEADER_CONTENT_TYPE);
    if (statusCode == 200) {
      String responseBody = new String(response.getEntity().getBytes(), StandardCharsets.UTF_8);
      JSONObject stabilityResponse = new JSONObject(responseBody);

      if (StringUtils.isNotBlank(contentType) &&
          contentType.contains("application/json") &&
          stabilityResponse.has("image")) {

        String base64Image = stabilityResponse.getString("image");
        // Clean base64 string if it contains data URI prefix
        if (base64Image.startsWith("data:image")) {
          base64Image = base64Image.substring(base64Image.indexOf(",") + 1);
        }
        // Log base64 length for debugging
        logger.debug("Base64 image length for image generation response process: {}", base64Image.length());

        StabilityAIImageRequestPayloadRecord payloadRecord = (StabilityAIImageRequestPayloadRecord) requestPayloadDTO;

        return new ImageGenerationRestResponse(null, List.of(new ImageData(base64Image, payloadRecord.prompt())));
      }
      logger.error("Unexpected response format: Content-Type is {} and response body is {}", contentType, responseBody);
      throw new IOException("Unexpected response format from Stability AI API");
    }
    throw handleErrorResponse(response, statusCode, InferenceErrorType.IMAGE_GENERATION_FAILURE);
  }
}
