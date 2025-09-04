package com.mulesoft.connectors.inference.internal.helpers.response.mapper;

import com.mulesoft.connectors.inference.api.metadata.AdditionalAttributes;
import com.mulesoft.connectors.inference.api.metadata.TokenUsage;
import com.mulesoft.connectors.inference.api.response.TextGenerationResponse;
import com.mulesoft.connectors.inference.api.response.ToolCall;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.TextResponseDTO;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class VertexAIResponseMapper extends DefaultResponseMapper {

  private final GeminiResponseMapper geminiResponseMapper;

  public VertexAIResponseMapper(ObjectMapper objectMapper) {
    super(objectMapper);
    geminiResponseMapper = new GeminiResponseMapper(objectMapper);
  }

  @Override
  public TokenUsage mapTokenUsageFromResponse(TextResponseDTO responseDTO) {
    return geminiResponseMapper.mapTokenUsageFromResponse(responseDTO);
  }

  @Override
  public AdditionalAttributes mapAdditionalAttributes(TextResponseDTO responseDTO, String modelName) {
    return geminiResponseMapper.mapAdditionalAttributes(responseDTO, modelName);
  }


  @Override
  public List<ToolCall> mapToolCalls(TextResponseDTO responseDTO) {

    return geminiResponseMapper.mapToolCalls(responseDTO);
  }

  @Override
  public TextGenerationResponse mapChatResponse(TextResponseDTO responseDTO) {
    return geminiResponseMapper.mapChatResponse(responseDTO);
  }

}
