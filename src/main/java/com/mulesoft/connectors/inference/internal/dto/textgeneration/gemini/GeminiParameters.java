package com.mulesoft.connectors.inference.internal.dto.textgeneration.gemini;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)public record GeminiParameters(String type,Map<String,GeminiProperty>properties,List<String>required)implements Serializable{}
