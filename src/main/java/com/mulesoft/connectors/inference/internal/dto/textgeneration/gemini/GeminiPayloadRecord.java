package com.mulesoft.connectors.inference.internal.dto.textgeneration.gemini;

import com.mulesoft.connectors.inference.internal.dto.textgeneration.TextGenerationRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.vision.VisionRequestPayloadDTO;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    List<FunctionDeclarationsWrapper> tools,

    @JsonIgnore Map<String, Object> additionalRequestAttributes
) implements TextGenerationRequestPayloadDTO, VisionRequestPayloadDTO {

    @Override
    public Map<String, Object> getAdditionalRequestAttributesMap() {
        return additionalRequestAttributes;
    }
}