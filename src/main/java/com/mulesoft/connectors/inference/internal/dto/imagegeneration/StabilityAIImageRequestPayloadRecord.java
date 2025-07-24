package com.mulesoft.connectors.inference.internal.dto.imagegeneration;

public record StabilityAIImageRequestPayloadRecord(String prompt,String responseFormat)implements ImageGenerationRequestPayloadDTO{

}
