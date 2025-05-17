package com.mulesoft.connectors.inference.api.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FunctionDefinitionRecord(String type,Function function) implements Serializable {
    @ConstructorProperties({"type", "function"})
    public FunctionDefinitionRecord {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Function(String name, String description,Parameters parameters) implements Serializable {
        @ConstructorProperties({"name", "description","parameters"})
        public Function {
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Parameters( String type, Map<String, Property> properties, List<String> required)
            implements Serializable {
        @ConstructorProperties({"type", "properties","required"})
        public Parameters {
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Property( String type, String description, List<String> enumValues) implements Serializable {
        @ConstructorProperties({"type", "description","enum"})
        public Property {
        }
    }
} 