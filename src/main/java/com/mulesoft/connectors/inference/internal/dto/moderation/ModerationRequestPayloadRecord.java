package com.mulesoft.connectors.inference.internal.dto.moderation;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)public record ModerationRequestPayloadRecord(Object input,String model,

@JsonIgnore Map<String,Object>additionalRequestAttributes){

@JsonAnyGetter public Map<String,Object>getAdditionalAttributes(){return additionalRequestAttributes!=null?additionalRequestAttributes:Map.of();}}
