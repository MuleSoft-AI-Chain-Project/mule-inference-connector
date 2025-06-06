package com.mulesoft.connectors.inference.internal.dto.textgeneration.response;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)public record ChatCompletionResponse(String id,String object,long created,String model,List<Choice>choices,TokenUsage usage,String serviceTier,String systemFingerprint){}
