package com.mulesoft.connectors.inference.internal.dto.imagegeneration;

public record XAIImageRequestPayloadRecord(String model, String prompt, String responseFormat) implements ImageGenerationRequestPayloadDTO {

}
