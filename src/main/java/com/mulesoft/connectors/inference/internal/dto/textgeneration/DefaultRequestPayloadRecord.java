package com.mulesoft.connectors.inference.internal.dto.textgeneration;

import com.mulesoft.connectors.inference.api.input.ChatPayloadRecord;
import com.mulesoft.connectors.inference.api.input.FunctionDefinitionRecord;

import java.util.List;

public record DefaultRequestPayloadRecord(String model, List<ChatPayloadRecord> messages, Number maxTokens,
                                          Number temperature, Number topP, List<FunctionDefinitionRecord> tools) implements TextGenerationRequestPayloadDTO {

}
