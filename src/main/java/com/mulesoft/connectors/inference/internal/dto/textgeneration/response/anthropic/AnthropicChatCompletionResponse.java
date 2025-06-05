package com.mulesoft.connectors.inference.internal.dto.textgeneration.response.anthropic;

import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.TextResponseDTO;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)public record AnthropicChatCompletionResponse(String id,String role,String model,List<Content>content,String stopReason,AnthropicTokenUsage usage)implements TextResponseDTO{}
