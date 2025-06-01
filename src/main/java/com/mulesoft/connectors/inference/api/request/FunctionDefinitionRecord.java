package com.mulesoft.connectors.inference.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.beans.ConstructorProperties;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FunctionDefinitionRecord(String type,Function function) implements Serializable {
    @ConstructorProperties({"type", "function"})
    public FunctionDefinitionRecord {
        // Compact constructor is intentionally empty because all fields are handled by the Java record.
    }
}