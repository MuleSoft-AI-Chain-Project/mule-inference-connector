package com.mulesoft.connectors.inference.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Property( String type, String description, List<String> enumValues) implements Serializable {
    @ConstructorProperties({"type", "description","enum"})
    public Property {
    }
}