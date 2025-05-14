package com.mulesoft.connectors.internal.dto;

import java.util.List;

public record DefaultRequestPayloadDTO(String model, List<ChatPayloadDTO> messages, Number maxTokens) implements RequestPayloadDTO {

}
