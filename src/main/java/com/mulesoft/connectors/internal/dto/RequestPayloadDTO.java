package com.mulesoft.connectors.internal.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record RequestPayloadDTO(String model, List<ChatPayloadDTO> messages, Number maxCompletionTokens) {

}
