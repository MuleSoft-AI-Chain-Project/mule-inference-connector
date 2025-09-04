package com.mulesoft.connectors.inference.internal.dto;

import com.mulesoft.connectors.inference.api.request.RequestHeader;

import java.util.List;

public record ParametersDTO(String modelName,String apiKey,Number maxTokens,Number temperature,Number topP,int timeout,
// custom headers that can be added from user input
List<RequestHeader>customHeaders){}
