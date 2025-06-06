package com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.anthropic;

import com.mulesoft.connectors.inference.internal.dto.textgeneration.TextGenerationRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.vision.VisionRequestPayloadDTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record VertexAIAnthropicPayloadRecord<T>(String anthropic_version, List<T> messages, Number maxTokens,
                                                Number temperature, Number topP, String system)
implements TextGenerationRequestPayloadDTO, VisionRequestPayloadDTO {
}
