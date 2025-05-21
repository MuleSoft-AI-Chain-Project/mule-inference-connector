package com.mulesoft.connectors.inference.internal.dto.imagegeneration.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ImageData(String b64Json,String revisedPrompt) {
}
