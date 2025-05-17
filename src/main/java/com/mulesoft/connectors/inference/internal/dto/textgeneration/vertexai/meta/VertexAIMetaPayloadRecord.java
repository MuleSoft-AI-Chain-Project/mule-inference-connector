package com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.meta;

import com.mulesoft.connectors.inference.api.request.ChatPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.TextGenerationRequestPayloadDTO;

import java.util.List;

public record VertexAIMetaPayloadRecord(String model, List<ChatPayloadRecord> messages, Number maxTokens,
                                        Number temperature, Number topP, boolean stream)
implements TextGenerationRequestPayloadDTO {
}
