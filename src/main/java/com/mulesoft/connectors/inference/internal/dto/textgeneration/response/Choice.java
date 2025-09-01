package com.mulesoft.connectors.inference.internal.dto.textgeneration.response;

import com.mulesoft.connectors.inference.api.metadata.FilterResult;

import java.util.Map;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)public record Choice(Message message,String finishReason,int index,
// content filter result(reference example: azureOpenAI)
Map<String,FilterResult>contentFilterResults){}
