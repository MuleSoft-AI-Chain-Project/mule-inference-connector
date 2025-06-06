package com.mulesoft.connectors.inference.internal.dto.textgeneration.response;

import com.mulesoft.connectors.inference.api.response.ToolCall;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)public record Message(String role,List<ToolCall>toolCalls,String content,String refusal,List<String>annotations){}
