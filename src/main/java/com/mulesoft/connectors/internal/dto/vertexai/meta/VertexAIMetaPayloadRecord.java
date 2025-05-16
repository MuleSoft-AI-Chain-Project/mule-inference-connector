package com.mulesoft.connectors.internal.dto.vertexai.meta;

import com.mulesoft.connectors.internal.dto.ChatPayloadDTO;
import com.mulesoft.connectors.internal.dto.RequestPayloadDTO;

import java.util.List;

public record VertexAIMetaPayloadRecord(String model, List<ChatPayloadDTO> messages, Number maxTokens,
                                        Number temperature, Number topP, boolean stream)
implements RequestPayloadDTO {
}
