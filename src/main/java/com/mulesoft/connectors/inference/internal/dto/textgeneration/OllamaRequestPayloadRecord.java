package com.mulesoft.connectors.inference.internal.dto.textgeneration;

import com.mulesoft.connectors.inference.api.request.ChatPayloadRecord;
import com.mulesoft.connectors.inference.api.request.FunctionDefinitionRecord;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

public record OllamaRequestPayloadRecord(String model,List<ChatPayloadRecord>messages,Number maxTokens,Number temperature,Number topP,boolean stream,List<FunctionDefinitionRecord>tools,

@JsonIgnore Map<String,Object>additionalRequestAttributes)implements TextGenerationRequestPayloadDTO{

@JsonAnyGetter public Map<String,Object>getAdditionalAttributes(){return additionalRequestAttributes!=null?additionalRequestAttributes:Map.of();}}
