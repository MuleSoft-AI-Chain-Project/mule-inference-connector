package com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.anthropic;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mulesoft.connectors.inference.api.request.ChatPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.TextGenerationRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.vision.VisionRequestPayloadDTO;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record VertexAIAnthropicPayloadRecord<T>(String anthropic_version, List<T> messages, Number maxTokens,
                                                Number temperature, Number topP, String system)
implements TextGenerationRequestPayloadDTO, VisionRequestPayloadDTO {
}
