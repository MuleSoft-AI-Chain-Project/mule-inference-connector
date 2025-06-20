package com.mulesoft.connectors.inference.internal.dto;

public record ParametersDTO(String modelName,String apiKey,Number maxTokens,Number temperature,Number topP,int timeout){}
