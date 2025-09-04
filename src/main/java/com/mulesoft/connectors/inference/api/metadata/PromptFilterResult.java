package com.mulesoft.connectors.inference.api.metadata;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)public record PromptFilterResult(int promptIndex,Map<String,FilterResult>contentFilterResults)implements Serializable{}
