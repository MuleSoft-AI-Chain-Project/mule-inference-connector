package com.mulesoft.connectors.inference.internal.dto.textgeneration.gemini;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)public record PartRecord(String text,@JsonProperty("functionCall")FunctionCall functionCall){}
