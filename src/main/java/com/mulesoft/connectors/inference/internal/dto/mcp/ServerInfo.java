package com.mulesoft.connectors.inference.internal.dto.mcp;

import com.mulesoft.connectors.inference.api.request.FunctionDefinitionRecord;

import java.util.List;

public record ServerInfo(String serverUrl,String serverName,List<FunctionDefinitionRecord>serverTools){}
