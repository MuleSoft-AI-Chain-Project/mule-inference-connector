package com.mulesoft.connectors.inference.internal.dto.vision;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

public record DefaultVisionRequestPayloadRecord(String model,List<Object>messages,Number maxTokens,Number temperature,Number topP,

@JsonIgnore Map<String,Object>additionalRequestAttributes)implements VisionRequestPayloadDTO{

@JsonAnyGetter public Map<String,Object>getAdditionalAttributes(){return additionalRequestAttributes!=null?additionalRequestAttributes:Map.of();}}
