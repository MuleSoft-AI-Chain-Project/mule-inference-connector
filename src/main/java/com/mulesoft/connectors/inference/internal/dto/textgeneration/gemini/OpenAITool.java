package com.mulesoft.connectors.inference.internal.dto.textgeneration.gemini;

import com.mulesoft.connectors.inference.api.request.Function;

public record OpenAITool(String type,Function function){}
