package com.mulesoft.connectors.internal.dto;

import java.util.List;

public record IBMWatsonRequestPayloadDTO(String modelId, String projectId, List<ChatPayloadDTO> messages,
                                         Number maxCompletionTokens, Number temperature, Number topP) implements RequestPayloadDTO{

}
