package com.mulesoft.connectors.inference.internal.dto.textgeneration.gemini;

import java.util.Map;

public record FunctionCall(String name,Map<String,Object>args){}
