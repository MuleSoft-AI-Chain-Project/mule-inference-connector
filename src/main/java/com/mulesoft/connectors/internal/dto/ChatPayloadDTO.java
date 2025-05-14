package com.mulesoft.connectors.internal.dto;

import java.beans.ConstructorProperties;

public record ChatPayloadDTO(String role, String content) {

    @ConstructorProperties({"role", "content"})
    public ChatPayloadDTO {
    }
}
