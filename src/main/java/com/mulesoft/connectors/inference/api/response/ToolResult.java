package com.mulesoft.connectors.inference.api.response;

import java.time.Instant;

public record ToolResult(
        String tool,
        Object result,
        String serverUrl,
        String serverName,
        Instant timestamp
) {} 