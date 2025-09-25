package com.mulesoft.connectors.inference.internal.helpers.response.mapper;

import com.mulesoft.connectors.inference.api.metadata.AdditionalAttributes;
import com.mulesoft.connectors.inference.api.metadata.TokenUsage;
import com.mulesoft.connectors.inference.api.response.Function;
import com.mulesoft.connectors.inference.api.response.TextGenerationResponse;
import com.mulesoft.connectors.inference.api.response.ToolCall;
import com.mulesoft.connectors.inference.api.response.ToolResult;
import com.mulesoft.connectors.inference.internal.dto.mcp.McpToolRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.TextResponseDTO;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.cohere.CohereChatCompletionResponse;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.cohere.Content;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CohereResponseMapper extends DefaultResponseMapper {

  public CohereResponseMapper(ObjectMapper objectMapper) {
    super(objectMapper);
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
                                    chatCompletionResponse.finishReason(), null, null);
  }

  @Override
  public List<ToolCall> mapToolCalls(TextResponseDTO responseDTO, Map<String, McpToolRecord> collectedTools) {

    var chatCompletionResponse = (CohereChatCompletionResponse) responseDTO;

    return convertToolCallsWithOriginalNames(chatCompletionResponse, collectedTools);
  }

  private List<ToolCall> convertToolCallsWithOriginalNames(CohereChatCompletionResponse chatResp,
                                                           Map<String, McpToolRecord> collectedTools) {
    return Optional.ofNullable(chatResp.message().toolCalls())
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

  @Override
  public TextGenerationResponse mapChatResponse(TextResponseDTO responseDTO) {
    var chatCompletionResponse = (CohereChatCompletionResponse) responseDTO;
    var chatRespFirstChoice = Optional.ofNullable(chatCompletionResponse.message())
        .flatMap(msg -> Optional.ofNullable(msg.content()).map(contents -> contents.get(0))).orElse(null);

    return new TextGenerationResponse(Optional.ofNullable(chatRespFirstChoice).map(Content::text).orElse(null),
                                      mapToolCalls(responseDTO, null), null);
  }

  @Override
  public TextGenerationResponse mapMcpExecuteToolsResponse(TextResponseDTO responseDTO, List<ToolResult> toolExecutionResult,
                                                           Map<String, McpToolRecord> collectedTools) {
    var chatCompletionResponse = (CohereChatCompletionResponse) responseDTO;
    var chatRespFirstChoice = Optional.ofNullable(chatCompletionResponse.message())
        .flatMap(msg -> Optional.ofNullable(msg.content()).map(contents -> contents.get(0))).orElse(null);

    return new TextGenerationResponse(Optional.ofNullable(chatRespFirstChoice).map(Content::text).orElse(null),
                                      mapToolCalls(responseDTO, collectedTools),
                                      toolExecutionResult);
  }
}
