package com.mulesoft.connectors.inference.internal.helpers.response.mapper;

import com.mulesoft.connectors.inference.api.metadata.AdditionalAttributes;
import com.mulesoft.connectors.inference.api.metadata.TokenUsage;
import com.mulesoft.connectors.inference.api.response.Function;
import com.mulesoft.connectors.inference.api.response.TextGenerationResponse;
import com.mulesoft.connectors.inference.api.response.ToolCall;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.gemini.PartRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.TextResponseDTO;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.gemini.Candidate;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.gemini.GeminiChatCompletionResponse;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        .filter(candidate -> candidate.content().parts() != null && !candidate.content().parts().isEmpty())
        .map(candidate -> candidate.content().parts().get(0).functionCall())
        .flatMap(fc -> Optional.ofNullable(fc).stream()
            .map(functionCall -> new ToolCall(
                                              UUID.randomUUID().toString(),
                                              "function",
                                              new Function(functionCall.name(),
                                                           convertToJsonString(functionCall.args())))))
        .toList();
  }

  @Override
  public TextGenerationResponse mapChatResponse(TextResponseDTO responseDTO) {

    var chatCompletionResponse = (GeminiChatCompletionResponse) responseDTO;
    var chatRespFirstChoice = chatCompletionResponse.candidates().stream().findFirst();

    return new TextGenerationResponse(chatRespFirstChoice.map(GeminiResponseMapper::mapTextResponse).orElse(null),
                                      mapToolCalls(responseDTO));
  }

  private static String mapTextResponse(Candidate x) {
    return Optional.ofNullable(x.content().parts())
        .flatMap(partRecords -> Optional.ofNullable(partRecords.get(0)).map(PartRecord::text)).orElse(null);
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
