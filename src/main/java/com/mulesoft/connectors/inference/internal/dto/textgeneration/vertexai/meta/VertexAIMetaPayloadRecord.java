package com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.meta;

import com.mulesoft.connectors.inference.api.request.ChatPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.TextGenerationRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.vision.VisionRequestPayloadDTO;

import java.util.List;

public record VertexAIMetaPayloadRecord<T>(String model, List<T> messages, Number maxTokens,
                                           Number temperature, Number topP, boolean stream)
implements TextGenerationRequestPayloadDTO, VisionRequestPayloadDTO {
}
