package com.mulesoft.connectors.inference.internal.dto.textgeneration;

import com.mulesoft.connectors.inference.api.request.ChatPayloadRecord;

import java.util.List;

public record AnthropicRequestPayloadRecord(String model,List<ChatPayloadRecord>messages,Number maxTokens,Number temperature,Number topP,List<AnthropicTollCallRecord>tools)implements TextGenerationRequestPayloadDTO{

}
