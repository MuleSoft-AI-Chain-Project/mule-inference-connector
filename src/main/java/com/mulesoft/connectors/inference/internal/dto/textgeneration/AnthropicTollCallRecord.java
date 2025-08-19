package com.mulesoft.connectors.inference.internal.dto.textgeneration;

import com.mulesoft.connectors.inference.api.request.FunctionSchema;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)public record AnthropicTollCallRecord(String name,String description,FunctionSchema inputSchema){}
