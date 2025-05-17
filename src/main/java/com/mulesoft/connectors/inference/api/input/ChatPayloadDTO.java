package com.mulesoft.connectors.inference.api.input;

import java.beans.ConstructorProperties;

public record ChatPayloadDTO(String role, String content) {

    @ConstructorProperties({"role", "content"})
    public ChatPayloadDTO {
    }
}
