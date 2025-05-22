package com.mulesoft.connectors.inference.internal.dto.moderation.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ModerationRestResponse(
    String id,
    String model,
    List<ModerationResult> results,
    Usage usage
) {}

