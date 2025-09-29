package com.mulesoft.connectors.inference.internal.helpers.payload;

import com.mulesoft.connectors.inference.internal.dto.imagegeneration.HugginFaceImageRequestPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.imagegeneration.ImageGenerationRequestPayloadDTO;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HuggingFaceRequestPayloadHelper extends RequestPayloadHelper {

  public HuggingFaceRequestPayloadHelper(ObjectMapper objectMapper) {
    super(objectMapper);
  }

  @Override
  public ImageGenerationRequestPayloadDTO createRequestImageGeneration(String model, String prompt,
                                                                       Map<String, Object> additionalRequestAttributes) {
    return new HugginFaceImageRequestPayloadRecord(prompt, additionalRequestAttributes);
  }
}
