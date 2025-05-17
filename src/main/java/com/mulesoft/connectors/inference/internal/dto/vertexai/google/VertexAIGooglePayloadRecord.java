package com.mulesoft.connectors.inference.internal.dto.vertexai.google;

import com.mulesoft.connectors.inference.api.input.ChatPayloadDTO;
import com.mulesoft.connectors.inference.api.input.FunctionDefinitionRecord;
import com.mulesoft.connectors.inference.internal.dto.RequestPayloadDTO;

import java.util.List;

public record VertexAIGooglePayloadRecord(List<ChatPayloadDTO> contents, SystemInstructionDTO systemInstruction,
                                          VertexAIGoogleGenerationConfigDTO generationConfig, List<String> safetySettings, List<FunctionDefinitionRecord> tools)
implements RequestPayloadDTO {
}
