package com.mulesoft.connectors.inference.internal.helpers.response.mapper;

import com.mulesoft.connectors.inference.api.metadata.AdditionalAttributes;
import com.mulesoft.connectors.inference.api.metadata.TokenUsage;
import com.mulesoft.connectors.inference.api.response.Function;
import com.mulesoft.connectors.inference.api.response.TextGenerationResponse;
import com.mulesoft.connectors.inference.api.response.ToolCall;
import com.mulesoft.connectors.inference.api.response.ToolResult;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.TextResponseDTO;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.ollama.OllamaChatCompletionResponse;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OllamaResponseMapper extends DefaultResponseMapper {

  private static final Logger logger = LoggerFactory.getLogger(OllamaResponseMapper.class);

  public OllamaResponseMapper(ObjectMapper objectMapper) {
    super(objectMapper);
  }

  @Override
  public TextGenerationResponse mapChatResponseWithToolExecutionResult(TextResponseDTO responseDTO,
                                                                       List<ToolResult> toolExecutionResult) {
    var chatCompletionResponse = (OllamaChatCompletionResponse) responseDTO;

    return new TextGenerationResponse(chatCompletionResponse.message().content(),
                                      this.mapToolCalls(responseDTO), toolExecutionResult);
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

  @Override
  public List<ToolCall> mapToolCalls(TextResponseDTO responseDTO) {

    var chatCompletionResponse = (OllamaChatCompletionResponse) responseDTO;

    return Optional.ofNullable(chatCompletionResponse.message().toolCalls())
        .map(toolCalls -> toolCalls.stream()
            .map(toolCall -> new ToolCall(
                                          null,
                                          "function",
                                          new Function(
                                                       toolCall.function().name(),
                                                       convertToJsonString(toolCall.function().arguments()))))
            .toList())
        .orElse(Collections.emptyList());
  }

  private String convertToJsonString(Map<String, Object> input) {
    try {
      return objectMapper.writeValueAsString(input);
    } catch (JsonProcessingException e) {
      logger.error("Error converting input to JSON string for tool call. Value for input field: {}", input, e);
      return null;
    }
  }
}
