package com.mulesoft.connectors.internal.dto.vertexai.google;

import com.mulesoft.connectors.internal.dto.ChatPayloadDTO;
import com.mulesoft.connectors.internal.dto.FunctionDefinitionRecord;
import com.mulesoft.connectors.internal.dto.RequestPayloadDTO;

import java.util.List;

public record VertexAIGooglePayloadRecord(List<ChatPayloadDTO> contents, SystemInstructionDTO systemInstruction,
                                          VertexAIGoogleGenerationConfigDTO generationConfig, List<String> safetySettings, List<FunctionDefinitionRecord> tools)
implements RequestPayloadDTO {
}
