package com.mulesoft.connectors.inference.internal.dto.imagegeneration;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record DefaultImageRequestPayloadRecord(String model,String prompt,String responseFormat,

@JsonIgnore Map<String,Object>additionalRequestAttributes)implements ImageGenerationRequestPayloadDTO{

@Override public Map<String,Object>getAdditionalRequestAttributesMap(){return additionalRequestAttributes;}}
