package com.mulesoft.connectors.inference.internal.dto.vertexai.meta;

import com.mulesoft.connectors.inference.api.input.ChatPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.RequestPayloadDTO;

import java.util.List;

public record VertexAIMetaPayloadRecord(String model, List<ChatPayloadDTO> messages, Number maxTokens,
                                        Number temperature, Number topP, boolean stream)
implements RequestPayloadDTO {
}
