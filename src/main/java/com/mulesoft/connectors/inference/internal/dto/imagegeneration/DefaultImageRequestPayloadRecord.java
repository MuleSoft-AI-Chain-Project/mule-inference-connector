package com.mulesoft.connectors.inference.internal.dto.imagegeneration;

public record DefaultImageRequestPayloadRecord(String prompt, String responseFormat) implements ImageGenerationRequestPayloadDTO {

}
