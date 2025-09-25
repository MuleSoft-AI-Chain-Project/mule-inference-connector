package com.mulesoft.connectors.inference.internal.helpers.response.mapper;

import com.mulesoft.connectors.inference.api.metadata.AdditionalAttributes;
import com.mulesoft.connectors.inference.api.metadata.TokenUsage;
import com.mulesoft.connectors.inference.api.response.Function;
import com.mulesoft.connectors.inference.api.response.TextGenerationResponse;
import com.mulesoft.connectors.inference.api.response.ToolCall;
import com.mulesoft.connectors.inference.api.response.ToolResult;
import com.mulesoft.connectors.inference.internal.dto.mcp.McpToolRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.ChatCompletionResponse;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.Choice;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.TextResponseDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    var chatCompletionResponse = (ChatCompletionResponse) responseDTO;
    var chatRespFirstChoice = chatCompletionResponse.choices().get(0);
    return new TextGenerationResponse(chatRespFirstChoice
        .message().content(), mapToolCalls(responseDTO, null), null);
  }

  public TextGenerationResponse mapMcpExecuteToolsResponse(TextResponseDTO responseDTO, List<ToolResult> toolExecutionResult,
                                                           Map<String, McpToolRecord> collectedTools) {
    var chatCompletionResponse = (ChatCompletionResponse) responseDTO;
    return new TextGenerationResponse(chatCompletionResponse.choices().get(0).message().content(),
                                      mapToolCalls(responseDTO, collectedTools),
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
                                    chatRespFirstChoice.finishReason(), chatRespFirstChoice.contentFilterResults(),
                                    chatCompletionResponse.promptFilterResults());
  }

  public List<ToolCall> mapToolCalls(TextResponseDTO responseDTO, Map<String, McpToolRecord> collectedTools) {
    var chatCompletionResponse = (ChatCompletionResponse) responseDTO;
    var chatRespFirstChoice = chatCompletionResponse.choices().get(0);
    return Optional.ofNullable(collectedTools).map(toolMap -> convertToolCallsWithOriginalNames(chatRespFirstChoice, toolMap))
        .orElse(chatRespFirstChoice.message().toolCalls());
  }

  private List<ToolCall> convertToolCallsWithOriginalNames(Choice chatRespFirstChoice,
                                                           Map<String, McpToolRecord> collectedTools) {
    return Optional.ofNullable(chatRespFirstChoice.message().toolCalls())
        .stream()
        .flatMap(List::stream)
        .map(toolCall -> {
          McpToolRecord toolRecord = collectedTools != null ? collectedTools.get(toolCall.function().name()) : null;
          String originalName = toolRecord != null ? toolRecord.originalName() : toolCall.function().name();
          return new ToolCall(toolCall.id(), toolCall.type(),
                              new Function(originalName, toolCall.function().arguments()));
        })
        .toList();
  }
}
