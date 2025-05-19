package com.mulesoft.connectors.inference.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Parameters(String type, Map<String, Property> properties,
                         List<String> required, boolean additionalProperties)
        implements Serializable {
    @ConstructorProperties({"type", "properties","required","additionalProperties"})
    public Parameters {
    }
}
