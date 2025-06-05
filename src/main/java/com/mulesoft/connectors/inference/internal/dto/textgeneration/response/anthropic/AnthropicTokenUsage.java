package com.mulesoft.connectors.inference.internal.dto.textgeneration.response.anthropic;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)public record AnthropicTokenUsage(int inputTokens,int outputTokens,String serviceTier,int cacheCreationInputTokens,int cacheReadInputTokens){}
