package com.mulesoft.connectors.internal.dto.vertexai.anthropic;

import com.mulesoft.connectors.internal.dto.ChatPayloadDTO;
import com.mulesoft.connectors.internal.dto.RequestPayloadDTO;

import java.util.List;

public record VertexAIAnthropicPayloadRecord(String anthropic_version, List<ChatPayloadDTO> messages, Number maxTokens)
implements RequestPayloadDTO {
}
