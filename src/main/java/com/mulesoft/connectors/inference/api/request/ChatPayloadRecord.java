package com.mulesoft.connectors.inference.api.request;

public record ChatPayloadRecord(String role, String content) implements Serializable {

    @ConstructorProperties({"role", "content"})
    public ChatPayloadRecord {
    }
}
