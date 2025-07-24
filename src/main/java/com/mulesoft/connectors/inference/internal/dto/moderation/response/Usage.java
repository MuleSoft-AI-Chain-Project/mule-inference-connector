package com.mulesoft.connectors.inference.internal.dto.moderation.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonIgnoreProperties(ignoreUnknown=true)@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)public record Usage(Integer promptTokens,Integer totalTokens,Integer completionTokens,Integer requestCount,Object promptTokenDetails){}
