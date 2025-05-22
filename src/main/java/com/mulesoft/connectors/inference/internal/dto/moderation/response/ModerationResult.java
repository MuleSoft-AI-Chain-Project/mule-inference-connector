package com.mulesoft.connectors.inference.internal.dto.moderation.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ModerationResult(
        Boolean flagged,
        Map<String, Boolean> categories,
        @JsonProperty("category_scores") Map<String, Double> categoryScores) {
}
