package com.mulesoft.connectors.inference.internal.dto.mcp;

import com.mulesoft.connectors.inference.api.request.Function;

public record McpToolRecord(String originalName, // Original name without prefix
String description,String configRef,Function function){

public String getName(){return configRef()+"__"+originalName(); // Unique name with prefix
}}
