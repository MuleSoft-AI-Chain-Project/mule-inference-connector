package com.mulesoft.connectors.inference.internal.dto.textgeneration.response.ollama;

import java.util.Map;

public record FunctionCall(String name,Map<String,Object>arguments){}
