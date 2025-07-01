package com.mulesoft.connectors.inference.internal.helpers.response.mapper;

import com.mulesoft.connectors.inference.api.metadata.AdditionalAttributes;
import com.mulesoft.connectors.inference.api.metadata.TokenUsage;
import com.mulesoft.connectors.inference.api.response.TextGenerationResponse;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.TextResponseDTO;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.ollama.OllamaChatCompletionResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class OllamaResponseMapper extends DefaultResponseMapper {

  public OllamaResponseMapper(ObjectMapper objectMapper) {
    super(objectMapper);
  }

  @Override
  public TextGenerationResponse mapChatResponse(TextResponseDTO responseDTO) {
    var chatCompletionResponse = (OllamaChatCompletionResponse) responseDTO;

    return new TextGenerationResponse(chatCompletionResponse.message().content(),
                                      mapToolCalls(responseDTO), null);
  }

  @Override
  public TokenUsage mapTokenUsageFromResponse(TextResponseDTO responseDTO) {
    var chatCompletionResponse = (OllamaChatCompletionResponse) responseDTO;

    return new TokenUsage(chatCompletionResponse.promptEvalCount(), chatCompletionResponse.evalCount(),
                          chatCompletionResponse.promptEvalCount() + chatCompletionResponse.evalCount());
  }

  @Override
  public AdditionalAttributes mapAdditionalAttributes(TextResponseDTO responseDTO, String modelName) {
    var chatCompletionResponse = (OllamaChatCompletionResponse) responseDTO;

    return new AdditionalAttributes(null, chatCompletionResponse.model(),
                                    chatCompletionResponse.doneReason());
  }
}
