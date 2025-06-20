package com.mulesoft.connectors.inference.internal.helpers.response.mapper;

import com.mulesoft.connectors.inference.api.metadata.AdditionalAttributes;
import com.mulesoft.connectors.inference.api.metadata.TokenUsage;
import com.mulesoft.connectors.inference.api.response.TextGenerationResponse;
import com.mulesoft.connectors.inference.api.response.ToolCall;
import com.mulesoft.connectors.inference.api.response.ToolResult;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.ChatCompletionResponse;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.TextResponseDTO;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultResponseMapper {

  private static final Logger logger = LoggerFactory.getLogger(DefaultResponseMapper.class);

  protected final ObjectMapper objectMapper;

  public DefaultResponseMapper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public TextGenerationResponse mapChatResponse(TextResponseDTO responseDTO) {
    return mapChatResponseWithToolExecutionResult(responseDTO, null);
  }

  public TextGenerationResponse mapChatResponseWithToolExecutionResult(TextResponseDTO responseDTO,
                                                                       List<ToolResult> toolExecutionResult) {
    var chatCompletionResponse = (ChatCompletionResponse) responseDTO;
    var chatRespFirstChoice = chatCompletionResponse.choices().get(0);
    return new TextGenerationResponse(chatRespFirstChoice
        .message().content(),
                                      chatRespFirstChoice
                                          .message().toolCalls(),
                                      toolExecutionResult);
  }

  public TokenUsage mapTokenUsageFromResponse(TextResponseDTO responseDTO) {
    var chatCompletionResponse = (ChatCompletionResponse) responseDTO;
    var chatRespUsage = chatCompletionResponse.usage();

    return new TokenUsage(chatRespUsage.promptTokens(), chatRespUsage.completionTokens(), chatRespUsage.totalTokens());
  }

  public AdditionalAttributes mapAdditionalAttributes(TextResponseDTO responseDTO, String modelName) {

    logger.debug("Map Additional attributes for model:{}", modelName);

    var chatCompletionResponse = (ChatCompletionResponse) responseDTO;
    var chatRespFirstChoice = chatCompletionResponse.choices().get(0);

    return new AdditionalAttributes(chatCompletionResponse.id(), chatCompletionResponse.model(),
                                    chatRespFirstChoice.finishReason());
  }

  public List<ToolCall> mapToolCalls(TextResponseDTO responseDTO) {
    var chatCompletionResponse = (ChatCompletionResponse) responseDTO;
    var chatRespFirstChoice = chatCompletionResponse.choices().get(0);

    return chatRespFirstChoice.message().toolCalls();
  }
}
