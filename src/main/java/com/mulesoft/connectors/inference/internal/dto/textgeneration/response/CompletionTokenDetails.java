package com.mulesoft.connectors.inference.internal.dto.textgeneration.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CompletionTokenDetails(
        int reasoningTokens,
        int audioTokens,
        int acceptedPredictionTokens,
        int rejectedPredictionTokens
) {}