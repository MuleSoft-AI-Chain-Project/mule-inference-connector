package com.mulesoft.connectors.internal.dto;

import java.util.List;

public record AzureOpenAIRequestPayloadDTO(List<ChatPayloadDTO> messages,
                                           Number maxCompletionTokens, Number temperature, Number topP,
                                           boolean stream, List<FunctionDefinitionRecord> tools) implements RequestPayloadDTO{

}
