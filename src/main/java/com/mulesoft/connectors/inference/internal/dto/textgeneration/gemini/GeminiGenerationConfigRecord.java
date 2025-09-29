package com.mulesoft.connectors.inference.internal.dto.textgeneration.gemini;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

public record GeminiGenerationConfigRecord(List<String>responseModalities,Number temperature,Number topP,Number maxOutputTokens,

@JsonIgnore Map<String,Object>additionalRequestAttributes){

@JsonAnyGetter public Map<String,Object>getAdditionalAttributes(){return additionalRequestAttributes!=null?additionalRequestAttributes:Map.of();}

}
