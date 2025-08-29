package com.mulesoft.connectors.inference.internal.dto.textgeneration.gemini;

//import com.mulesoft.connectors.inference.api.request.Function;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.TextGenerationRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.vision.VisionRequestPayloadDTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record GeminiPayloadRecord<T>(
    List<T> contents,
    SystemInstructionRecord systemInstruction,
    GeminiGenerationConfigRecord generationConfig,
    List<String> safetySettings,

    @JsonProperty("tools")
    List<FunctionDeclarationsWrapper> tools // ✅ now wrapped correctly
) implements TextGenerationRequestPayloadDTO, VisionRequestPayloadDTO {
}