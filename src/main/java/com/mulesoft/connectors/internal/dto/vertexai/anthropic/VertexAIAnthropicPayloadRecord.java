package com.mulesoft.connectors.internal.dto.vertexai.anthropic;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mulesoft.connectors.internal.dto.ChatPayloadDTO;
import com.mulesoft.connectors.internal.dto.RequestPayloadDTO;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record VertexAIAnthropicPayloadRecord(String anthropic_version, List<ChatPayloadDTO> messages, Number maxTokens,
                                             Number temperature, Number topP, String system)
implements RequestPayloadDTO {
}
