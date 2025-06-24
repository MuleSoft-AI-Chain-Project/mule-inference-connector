package com.mulesoft.connectors.inference.internal.helpers.payload;

import com.mulesoft.connectors.inference.internal.dto.imagegeneration.StabilityAIImageRequestPayloadRecord;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StabilityAIRequestPayloadHelper extends RequestPayloadHelper {

  public StabilityAIRequestPayloadHelper(ObjectMapper objectMapper) {
    super(objectMapper);
  }

  @Override
  public StabilityAIImageRequestPayloadRecord createRequestImageGeneration(String model, String prompt) {
    return new StabilityAIImageRequestPayloadRecord(prompt, null);
  }
}
