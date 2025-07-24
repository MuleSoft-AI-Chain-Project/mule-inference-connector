package com.mulesoft.connectors.inference.internal.dto.textgeneration.response.ollama;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)public record Message(String role,String content,List<ToolCall>toolCalls){}
