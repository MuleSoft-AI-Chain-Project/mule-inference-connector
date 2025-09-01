package com.mulesoft.connectors.inference.internal.helpers.response.mapper;

import com.mulesoft.connectors.inference.api.metadata.AdditionalAttributes;
import com.mulesoft.connectors.inference.api.metadata.TokenUsage;
import com.mulesoft.connectors.inference.api.response.Function;
import com.mulesoft.connectors.inference.api.response.TextGenerationResponse;
import com.mulesoft.connectors.inference.api.response.ToolCall;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.TextResponseDTO;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.gemini.Candidate;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.gemini.GeminiChatCompletionResponse;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeminiResponseMapper extends DefaultResponseMapper {

  private static final Logger logger = LoggerFactory.getLogger(GeminiResponseMapper.class);

  public GeminiResponseMapper(ObjectMapper objectMapper) {
    super(objectMapper);
  }

  @Override
  public TokenUsage mapTokenUsageFromResponse(TextResponseDTO responseDTO) {
    var chatCompletionResponse = (GeminiChatCompletionResponse) responseDTO;
    var chatRespUsage = chatCompletionResponse.usageMetadata();

    return new TokenUsage(chatRespUsage.promptTokenCount(), chatRespUsage.candidatesTokenCount(),
                          chatRespUsage.totalTokenCount());
  }

  @Override
  public AdditionalAttributes mapAdditionalAttributes(TextResponseDTO responseDTO, String modelName) {

    logger.debug("Map Additional attributes for model:{}", modelName);

    var chatCompletionResponse = (GeminiChatCompletionResponse) responseDTO;
    var chatRespFirstChoice = chatCompletionResponse.candidates().stream().findFirst();

    return new AdditionalAttributes(chatCompletionResponse.responseId(), chatCompletionResponse.modelVersion(),
                                    chatRespFirstChoice.map(Candidate::finishReason).orElse("Unknown"), null, null);
  }

  @Override
  public List<ToolCall> mapToolCalls(TextResponseDTO responseDTO) {
    GeminiChatCompletionResponse geminiResponse = (GeminiChatCompletionResponse) responseDTO;

    return geminiResponse.candidates().stream()
        .map(candidate -> candidate.content().parts().get(0).functionCall())
        .filter(Objects::nonNull)
        .map(fc -> {
          try {
            String argsJson = objectMapper.writeValueAsString(fc.args());
            return new ToolCall(
                                UUID.randomUUID().toString(),
                                "function",
                                new Function(fc.name(), argsJson));
          } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize function call args", e);
          }
        })
        .toList();
  }

  @Override
  public TextGenerationResponse mapChatResponse(TextResponseDTO responseDTO) {

    var chatCompletionResponse = (GeminiChatCompletionResponse) responseDTO;
    var chatRespFirstChoice = chatCompletionResponse.candidates().stream().findFirst();

    return new TextGenerationResponse(chatRespFirstChoice.map(x -> x.content().parts().get(0).text()).orElse(null),
                                      mapToolCalls(responseDTO));
  }

}
