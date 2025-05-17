package com.mulesoft.connectors.inference.internal.dto.textgeneration;

import com.mulesoft.connectors.inference.api.input.ChatPayloadDTO;
import com.mulesoft.connectors.inference.api.input.FunctionDefinitionRecord;

import java.util.List;

public record IBMWatsonRequestPayloadRecord(String modelId, String projectId, List<ChatPayloadDTO> messages,
                                            Number maxCompletionTokens, Number temperature, Number topP, List<FunctionDefinitionRecord> tools) implements TextGenerationRequestPayloadDTO {

}
