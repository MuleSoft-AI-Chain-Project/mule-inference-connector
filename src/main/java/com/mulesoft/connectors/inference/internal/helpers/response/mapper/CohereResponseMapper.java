package com.mulesoft.connectors.inference.internal.helpers.response.mapper;

import com.mulesoft.connectors.inference.api.metadata.AdditionalAttributes;
import com.mulesoft.connectors.inference.api.metadata.TokenUsage;
import com.mulesoft.connectors.inference.api.response.TextGenerationResponse;
import com.mulesoft.connectors.inference.api.response.ToolCall;
import com.mulesoft.connectors.inference.api.response.ToolResult;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.TextResponseDTO;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.cohere.CohereChatCompletionResponse;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.cohere.Content;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CohereResponseMapper extends DefaultResponseMapper {

  public CohereResponseMapper(ObjectMapper objectMapper) {
    super(objectMapper);
  }

  @Override
  public TextGenerationResponse mapChatResponse(TextResponseDTO responseDTO) {
    return mapChatResponseWithToolExecutionResult(responseDTO, null);
  }

  @Override
  public TokenUsage mapTokenUsageFromResponse(TextResponseDTO responseDTO) {
    var chatCompletionResponse = (CohereChatCompletionResponse) responseDTO;
    var chatRespUsage = chatCompletionResponse.usage();

    return new TokenUsage(chatRespUsage.billedUnits().inputTokens(), chatRespUsage.billedUnits().outputTokens(),
                          chatRespUsage.billedUnits().inputTokens() + chatRespUsage.billedUnits().outputTokens());
  }

  @Override
  public AdditionalAttributes mapAdditionalAttributes(TextResponseDTO responseDTO, String modelName) {
    var chatCompletionResponse = (CohereChatCompletionResponse) responseDTO;

    return new AdditionalAttributes(chatCompletionResponse.id(), modelName,
                                    chatCompletionResponse.finishReason());
  }

  @Override
  public List<ToolCall> mapToolCalls(TextResponseDTO responseDTO) {

    var chatCompletionResponse = (CohereChatCompletionResponse) responseDTO;

    return chatCompletionResponse.message().toolCalls();
  }

  @Override
  public TextGenerationResponse mapChatResponseWithToolExecutionResult(TextResponseDTO responseDTO,
                                                                       List<ToolResult> toolExecutionResult) {
    var chatCompletionResponse = (CohereChatCompletionResponse) responseDTO;
    var chatRespFirstChoice = Optional.ofNullable(chatCompletionResponse.message())
        .flatMap(msg -> Optional.ofNullable(msg.content()).map(contents -> contents.get(0))).orElse(null);

    return new TextGenerationResponse(Optional.ofNullable(chatRespFirstChoice).map(Content::text).orElse(null),
                                      mapToolCalls(responseDTO),
                                      toolExecutionResult);
  }
}
