package com.mulesoft.connectors.internal.dto.vertexai.meta;

import com.mulesoft.connectors.internal.dto.ChatPayloadDTO;
import com.mulesoft.connectors.internal.dto.RequestPayloadDTO;

import java.util.List;

public record VertexAIMetaPayloadRecord(String anthropic_version, List<ChatPayloadDTO> messages, Number maxTokens,boolean stream)
implements RequestPayloadDTO {
}
