package com.mulesoft.connectors.inference.internal.dto.textgeneration.gemini;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)public record GeminiFunction(String name,String description,GeminiParameters parameters)implements Serializable{}
