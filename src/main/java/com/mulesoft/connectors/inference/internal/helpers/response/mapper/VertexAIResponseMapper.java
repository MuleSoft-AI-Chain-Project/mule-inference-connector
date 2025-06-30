package com.mulesoft.connectors.inference.internal.helpers.response.mapper;

import com.mulesoft.connectors.inference.api.metadata.AdditionalAttributes;
import com.mulesoft.connectors.inference.api.metadata.TokenUsage;
import com.mulesoft.connectors.inference.api.response.TextGenerationResponse;
import com.mulesoft.connectors.inference.api.response.ToolCall;
import com.mulesoft.connectors.inference.api.response.ToolResult;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.TextResponseDTO;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.vertexai.Candidate;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.vertexai.VertexAiChatCompletionResponse;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VertexAIResponseMapper extends DefaultResponseMapper {

  private static final Logger logger = LoggerFactory.getLogger(VertexAIResponseMapper.class);

  public VertexAIResponseMapper(ObjectMapper objectMapper) {
    super(objectMapper);
  }

  @Override
  public TextGenerationResponse mapChatResponse(TextResponseDTO responseDTO) {
    var chatCompletionResponse = (VertexAiChatCompletionResponse) responseDTO;
    var chatRespFirstChoice = chatCompletionResponse.candidates().stream().findFirst();

    return new TextGenerationResponse(chatRespFirstChoice.map(x -> x.content().parts().get(0).text()).orElse(null),
                                      mapToolCalls(responseDTO), null);
  }

  @Override
  public TokenUsage mapTokenUsageFromResponse(TextResponseDTO responseDTO) {
    var chatCompletionResponse = (VertexAiChatCompletionResponse) responseDTO;
    var chatRespUsage = chatCompletionResponse.usageMetadata();

    return new TokenUsage(chatRespUsage.promptTokenCount(), chatRespUsage.candidatesTokenCount(),
                          chatRespUsage.totalTokenCount());
  }

  @Override
  public AdditionalAttributes mapAdditionalAttributes(TextResponseDTO responseDTO, String modelName) {

    logger.debug("Map Additional attributes for model:{}", modelName);

    var chatCompletionResponse = (VertexAiChatCompletionResponse) responseDTO;
    var chatRespFirstChoice = chatCompletionResponse.candidates().stream().findFirst();

    return new AdditionalAttributes(chatCompletionResponse.responseId(), chatCompletionResponse.modelVersion(),
                                    chatRespFirstChoice.map(Candidate::finishReason).orElse("Unknown"));
  }

  @Override
  public List<ToolCall> mapToolCalls(TextResponseDTO responseDTO) {

    var chatCompletionResponse = (VertexAiChatCompletionResponse) responseDTO;

    return Collections.emptyList();
  }

  @Override
  public TextGenerationResponse mapChatResponseWithToolExecutionResult(TextResponseDTO responseDTO,
                                                                       List<ToolResult> toolExecutionResult) {

    return new TextGenerationResponse(null,
                                      mapToolCalls(responseDTO),
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
}
