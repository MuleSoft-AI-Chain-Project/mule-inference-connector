package com.mulesoft.connectors.inference.internal.dto.imagegeneration;

public record OpenAIImageRequestPayloadRecord(String model,String prompt,String responseFormat)implements ImageGenerationRequestPayloadDTO{

}
