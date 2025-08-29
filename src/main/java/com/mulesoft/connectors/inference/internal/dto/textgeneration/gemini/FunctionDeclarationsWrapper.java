package com.mulesoft.connectors.inference.internal.dto.textgeneration.gemini;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)public record FunctionDeclarationsWrapper(

@JsonProperty("functionDeclarations")List<GeminiFunction>functionDeclarations

)implements Serializable{}
