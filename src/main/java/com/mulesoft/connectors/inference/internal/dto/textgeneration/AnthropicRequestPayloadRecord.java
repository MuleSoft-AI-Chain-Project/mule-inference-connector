package com.mulesoft.connectors.inference.internal.dto.textgeneration;

import com.mulesoft.connectors.inference.api.request.ChatPayloadRecord;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record AnthropicRequestPayloadRecord(String model,List<ChatPayloadRecord>messages,Number maxTokens,Number temperature,Number topP,List<AnthropicToolCallRecord>tools,

@JsonIgnore Map<String,Object>additionalRequestAttributes)implements TextGenerationRequestPayloadDTO{

@Override public Map<String,Object>getAdditionalRequestAttributesMap(){return additionalRequestAttributes;}

}
