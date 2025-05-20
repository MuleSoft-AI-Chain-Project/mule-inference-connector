package com.mulesoft.connectors.inference.api.response;

public record ToolCall(
        String id,
        Function function,
        int index
) {
}
