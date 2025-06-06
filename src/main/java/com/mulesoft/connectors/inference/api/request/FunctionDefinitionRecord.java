package com.mulesoft.connectors.inference.api.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)public record FunctionDefinitionRecord(String type,Function function)implements Serializable{}
