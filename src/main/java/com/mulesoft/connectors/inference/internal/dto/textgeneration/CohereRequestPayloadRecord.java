package com.mulesoft.connectors.inference.internal.dto.textgeneration;

import com.mulesoft.connectors.inference.api.request.ChatPayloadRecord;
import com.mulesoft.connectors.inference.api.request.FunctionDefinitionRecord;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record CohereRequestPayloadRecord(String model,List<ChatPayloadRecord>messages,Number maxTokens,Number temperature,List<FunctionDefinitionRecord>tools,

@JsonIgnore Map<String,Object>additionalRequestAttributes)implements TextGenerationRequestPayloadDTO{

@Override public Map<String,Object>getAdditionalRequestAttributesMap(){return additionalRequestAttributes;}}
