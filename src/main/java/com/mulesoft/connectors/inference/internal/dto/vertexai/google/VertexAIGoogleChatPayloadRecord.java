package com.mulesoft.connectors.inference.internal.dto.vertexai.google;

import com.mulesoft.connectors.inference.internal.dto.RequestPayloadDTO;

import java.util.List;

public record VertexAIGoogleChatPayloadRecord(List<UserContentRecord> contents, SystemInstructionDTO systemInstruction,
                                              VertexAIGoogleGenerationConfigDTO generationConfig, List<String> safetySettings, List<String> tools)
implements RequestPayloadDTO {
}
