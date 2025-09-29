package com.mulesoft.connectors.inference.internal.dto.imagegeneration;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

public record DefaultImageRequestPayloadRecord(String model,String prompt,String responseFormat,

@JsonIgnore Map<String,Object>additionalRequestAttributes)implements ImageGenerationRequestPayloadDTO{

@JsonAnyGetter public Map<String,Object>getAdditionalAttributes(){return additionalRequestAttributes!=null?additionalRequestAttributes:Map.of();}}
