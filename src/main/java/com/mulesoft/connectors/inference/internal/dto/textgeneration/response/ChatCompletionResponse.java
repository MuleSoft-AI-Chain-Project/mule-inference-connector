package com.mulesoft.connectors.inference.internal.dto.textgeneration.response;

import com.mulesoft.connectors.inference.api.metadata.PromptFilterResult;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)public record ChatCompletionResponse(String id,String object,long created,String model,List<Choice>choices,TokenUsage usage,String serviceTier,String systemFingerprint,
// prompt filter result(reference example: azureOpenAI)
List<PromptFilterResult>promptFilterResults)implements TextResponseDTO{}
