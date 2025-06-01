package com.mulesoft.connectors.inference.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.beans.ConstructorProperties;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FunctionDefinitionRecord(String type,Function function) implements Serializable {
}