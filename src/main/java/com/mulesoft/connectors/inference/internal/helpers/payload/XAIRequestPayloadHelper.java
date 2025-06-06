package com.mulesoft.connectors.inference.internal.helpers.payload;

import com.mulesoft.connectors.inference.internal.dto.imagegeneration.ImageGenerationRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.imagegeneration.XAIImageRequestPayloadRecord;

import com.fasterxml.jackson.databind.ObjectMapper;

public class XAIRequestPayloadHelper extends RequestPayloadHelper {

  public XAIRequestPayloadHelper(ObjectMapper objectMapper) {
    super(objectMapper);
  }

  @Override
  public ImageGenerationRequestPayloadDTO createRequestImageGeneration(String model, String prompt) {

    return new XAIImageRequestPayloadRecord(model, prompt, "b64_json");
  }
}
