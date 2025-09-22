package com.mulesoft.connectors.inference.internal.dto.mcp;

import com.mulesoft.connectors.inference.api.request.Function;

public record McpToolRecord(String originalName, // Original name without prefix
String description,
// Config ref of the mcp-client
String configRef,Function function){

public String getName(){
// Unique name with prefix
return configRef()+"__"+originalName();}}
