package com.mulesoft.connectors.inference.internal.helpers.response.mapper;

import com.mulesoft.connectors.inference.api.metadata.AdditionalAttributes;
import com.mulesoft.connectors.inference.api.metadata.TokenUsage;
import com.mulesoft.connectors.inference.api.response.Function;
import com.mulesoft.connectors.inference.api.response.TextGenerationResponse;
import com.mulesoft.connectors.inference.api.response.ToolCall;
import com.mulesoft.connectors.inference.api.response.ToolResult;
import com.mulesoft.connectors.inference.internal.dto.mcp.McpToolRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.TextResponseDTO;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.anthropic.AnthropicChatCompletionResponse;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.anthropic.Content;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnthropicResponseMapper extends DefaultResponseMapper {

  private static final Logger logger = LoggerFactory.getLogger(AnthropicResponseMapper.class);

  public AnthropicResponseMapper(ObjectMapper objectMapper) {
    super(objectMapper);
  }

  @Override
  public TokenUsage mapTokenUsageFromResponse(TextResponseDTO responseDTO) {
    var chatCompletionResponse = (AnthropicChatCompletionResponse) responseDTO;
    var chatRespUsage = chatCompletionResponse.usage();

    return new TokenUsage(chatRespUsage.inputTokens(), chatRespUsage.outputTokens(),
                          chatRespUsage.inputTokens() + chatRespUsage.outputTokens());
  }

  @Override
  public AdditionalAttributes mapAdditionalAttributes(TextResponseDTO responseDTO, String modelName) {

    logger.debug("Map Additional attributes for model:{}", modelName);

    var chatCompletionResponse = (AnthropicChatCompletionResponse) responseDTO;

    return new AdditionalAttributes(chatCompletionResponse.id(), chatCompletionResponse.model(),
                                    chatCompletionResponse.stopReason(), null, null);
  }

  @Override
  public List<ToolCall> mapToolCalls(TextResponseDTO responseDTO, Map<String, McpToolRecord> collectedTools) {

    var chatCompletionResponse = (AnthropicChatCompletionResponse) responseDTO;

    return chatCompletionResponse.content().stream()
        .filter(content -> "tool_use".equals(content.type()))
        .map(content -> new ToolCall(
                                     content.id(),
                                     "function",
                                     new Function(convertToolCallsWithOriginalNames(content.name(), collectedTools),
                                                  convertToJsonString(content.input()))))
        .toList();
  }

  @Override
  public TextGenerationResponse mapChatResponse(TextResponseDTO responseDTO) {
    var chatCompletionResponse = (AnthropicChatCompletionResponse) responseDTO;
    var chatRespFirstChoice = chatCompletionResponse.content().stream()
        .filter(x -> "text".equals(x.type()) && StringUtils.isNotBlank(x.text())).findFirst();
    return new TextGenerationResponse(chatRespFirstChoice.map(Content::text).orElse(null),
                                      mapToolCalls(responseDTO, null), null);
  }

  @Override
  public TextGenerationResponse mapMcpExecuteToolsResponse(TextResponseDTO responseDTO, List<ToolResult> toolExecutionResult,
                                                           Map<String, McpToolRecord> collectedTools) {
    var chatCompletionResponse = (AnthropicChatCompletionResponse) responseDTO;
    var chatRespFirstChoice = chatCompletionResponse.content().stream()
        .filter(x -> "text".equals(x.type()) && StringUtils.isNotBlank(x.text())).findFirst();
    return new TextGenerationResponse(chatRespFirstChoice.map(Content::text).orElse(null),
                                      mapToolCalls(responseDTO, collectedTools),
                                      toolExecutionResult);
  }

  private String convertToJsonString(Map<String, Object> input) {
    try {
      return objectMapper.writeValueAsString(input);
    } catch (JsonProcessingException e) {
      logger.error("Error converting input to JSON string for tool call. Value for input field: {}", input, e);
      return null;
    }
  }

  private String convertToolCallsWithOriginalNames(String funcName,
                                                   Map<String, McpToolRecord> collectedTools) {
    return Optional.ofNullable(collectedTools)
        .map(toolMap -> Optional.ofNullable(collectedTools.get(funcName))
            .map(McpToolRecord::originalName).orElse(funcName))
        .orElse(funcName);
  }
}
