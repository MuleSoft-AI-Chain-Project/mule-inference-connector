package com.mulesoft.connectors.inference.api.input;

import java.beans.ConstructorProperties;
import java.io.Serializable;

public record ChatPayloadRecord(String role, String content) implements Serializable {

    @ConstructorProperties({"role", "content"})
    public ChatPayloadRecord {
    }
}
