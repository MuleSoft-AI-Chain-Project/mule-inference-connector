package com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.google;

import com.mulesoft.connectors.inference.api.request.FunctionDefinitionRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.TextGenerationRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.vision.VisionRequestPayloadDTO;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record VertexAIGooglePayloadRecord<T>(List<T> contents, SystemInstructionRecord systemInstruction,
                                             VertexAIGoogleGenerationConfigRecord generationConfig, List<String> safetySettings, List<FunctionDefinitionRecord> tools)
implements TextGenerationRequestPayloadDTO, VisionRequestPayloadDTO {
}
