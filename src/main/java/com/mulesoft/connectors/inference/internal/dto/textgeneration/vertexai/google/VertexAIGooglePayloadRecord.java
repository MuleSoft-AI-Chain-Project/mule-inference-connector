package com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.google;

import com.mulesoft.connectors.inference.api.request.ChatPayloadRecord;
import com.mulesoft.connectors.inference.api.request.FunctionDefinitionRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.TextGenerationRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.vision.VisionRequestPayloadDTO;

import java.util.List;

public record VertexAIGooglePayloadRecord<T>(List<T> contents, SystemInstructionRecord systemInstruction,
                                             VertexAIGoogleGenerationConfigRecord generationConfig, List<String> safetySettings, List<FunctionDefinitionRecord> tools)
implements TextGenerationRequestPayloadDTO, VisionRequestPayloadDTO {
}
