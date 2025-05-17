package com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.google;

import com.mulesoft.connectors.inference.api.input.ChatPayloadDTO;
import com.mulesoft.connectors.inference.api.input.FunctionDefinitionRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.RequestPayloadDTO;

import java.util.List;

public record VertexAIGooglePayloadRecord(List<ChatPayloadDTO> contents, SystemInstructionRecord systemInstruction,
                                          VertexAIGoogleGenerationConfigRecord generationConfig, List<String> safetySettings, List<FunctionDefinitionRecord> tools)
implements RequestPayloadDTO {
}
