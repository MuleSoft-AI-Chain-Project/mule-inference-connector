package com.mulesoft.connectors.inference.internal.dto.textgeneration.response;

public record ToolCall(
        String id,
        Function function,
        int index
) {
}
