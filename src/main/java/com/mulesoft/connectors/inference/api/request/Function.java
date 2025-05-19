package com.mulesoft.connectors.inference.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.beans.ConstructorProperties;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Function(String name, String description,
                       Parameters parameters) implements Serializable {
    @ConstructorProperties({"name", "description","parameters"})
    public Function {
    }
}
