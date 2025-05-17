package com.mulesoft.connectors.inference.internal.dto.textgeneration;

import com.mulesoft.connectors.inference.api.input.ChatPayloadRecord;
import com.mulesoft.connectors.inference.api.input.FunctionDefinitionRecord;

import java.util.List;

public record OllamaRequestPayloadRecord(String model, List<ChatPayloadRecord> messages,
                                         Number maxTokens, Number temperature, Number topP, boolean stream,
                                         List<FunctionDefinitionRecord> tools) implements TextGenerationRequestPayloadDTO {

}
