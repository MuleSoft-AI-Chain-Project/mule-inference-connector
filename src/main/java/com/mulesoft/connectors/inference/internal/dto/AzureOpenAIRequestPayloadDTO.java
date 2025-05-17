package com.mulesoft.connectors.inference.internal.dto;

import com.mulesoft.connectors.inference.api.input.ChatPayloadDTO;
import com.mulesoft.connectors.inference.api.input.FunctionDefinitionRecord;

import java.util.List;

public record AzureOpenAIRequestPayloadDTO(List<ChatPayloadDTO> messages,
                                           Number maxCompletionTokens, Number temperature, Number topP,
                                           boolean stream, List<FunctionDefinitionRecord> tools) implements RequestPayloadDTO{

}
