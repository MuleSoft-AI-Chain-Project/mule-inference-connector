package com.mulesoft.connectors.inference.internal.helpers.response;

import org.mule.runtime.http.api.domain.message.response.HttpResponse;

import com.mulesoft.connectors.inference.internal.dto.imagegeneration.HugginFaceImageRequestPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.imagegeneration.ImageGenerationRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.imagegeneration.response.ImageData;
import com.mulesoft.connectors.inference.internal.dto.imagegeneration.response.ImageGenerationRestResponse;
import com.mulesoft.connectors.inference.internal.error.InferenceErrorType;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HuggingFaceHttpResponseHelper extends HttpResponseHelper {

  public HuggingFaceHttpResponseHelper(ObjectMapper objectMapper) {
    super(objectMapper);
  }

  @Override
  public ImageGenerationRestResponse processImageGenerationResponse(ImageGenerationRequestPayloadDTO requestPayloadDTO,
                                                                    HttpResponse response)
      throws IOException {
    int statusCode = response.getStatusCode();

    if (statusCode == 200) {
      String base64Image = encodeImageToBase64(response.getEntity().getBytes());

      HugginFaceImageRequestPayloadRecord payload = (HugginFaceImageRequestPayloadRecord) requestPayloadDTO;

      return new ImageGenerationRestResponse(null, List.of(new ImageData(base64Image, payload.inputs())));
    }
    throw handleErrorResponse(response, statusCode, InferenceErrorType.IMAGE_GENERATION_FAILURE);
  }

  private String encodeImageToBase64(byte[] imageBytes) {
    return Base64.getEncoder().encodeToString(imageBytes);
  }
}
