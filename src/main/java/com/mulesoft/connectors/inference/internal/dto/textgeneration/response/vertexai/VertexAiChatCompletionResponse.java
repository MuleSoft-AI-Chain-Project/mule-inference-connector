package com.mulesoft.connectors.inference.internal.dto.textgeneration.response.vertexai;

import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.TextResponseDTO;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)public record VertexAiChatCompletionResponse(List<Candidate>candidates,UsageMetadata usageMetadata,String modelVersion,String createTime,String responseId)implements TextResponseDTO{}
