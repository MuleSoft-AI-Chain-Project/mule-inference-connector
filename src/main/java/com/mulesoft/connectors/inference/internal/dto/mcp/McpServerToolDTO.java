package com.mulesoft.connectors.inference.internal.dto.mcp;

import java.io.Serializable;

public record McpServerToolDTO(String name,String description,String inputSchema)implements Serializable{}
