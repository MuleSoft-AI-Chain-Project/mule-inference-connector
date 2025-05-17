package com.mulesoft.connectors.inference.internal.dto;

import com.mulesoft.connectors.inference.api.input.ChatPayloadDTO;
import com.mulesoft.connectors.inference.api.input.FunctionDefinitionRecord;

import java.util.List;

public record DefaultRequestPayloadDTO(String model, List<ChatPayloadDTO> messages, Number maxTokens,
                                       Number temperature, Number topP, List<FunctionDefinitionRecord> tools) implements RequestPayloadDTO {

}
