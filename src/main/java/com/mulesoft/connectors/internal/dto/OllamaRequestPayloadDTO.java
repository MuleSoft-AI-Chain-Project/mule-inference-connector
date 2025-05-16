package com.mulesoft.connectors.internal.dto;

import java.util.List;

public record OllamaRequestPayloadDTO(String model, List<ChatPayloadDTO> messages,
                                      Number maxTokens, Number temperature, Number topP, boolean stream,
                                      List<FunctionDefinitionRecord> tools) implements RequestPayloadDTO{

}
