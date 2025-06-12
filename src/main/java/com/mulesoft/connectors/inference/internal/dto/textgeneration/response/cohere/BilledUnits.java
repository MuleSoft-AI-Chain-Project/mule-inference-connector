package com.mulesoft.connectors.inference.internal.dto.textgeneration.response.cohere;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)public record BilledUnits(int inputTokens,int outputTokens){}
