package com.mulesoft.connectors.inference.internal.helpers.response.mapper;

import com.mulesoft.connectors.inference.api.metadata.AdditionalAttributes;
import com.mulesoft.connectors.inference.api.metadata.TokenUsage;
import com.mulesoft.connectors.inference.api.response.TextGenerationResponse;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.TextResponseDTO;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.anthropic.AnthropicChatCompletionResponse;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.anthropic.Content;

import org.apache.logging.log4j.util.Strings;

public class AnthropicResponseMapper extends DefaultResponseMapper {

  @Override
  public TextGenerationResponse mapChatResponse(TextResponseDTO responseDTO) {
    var chatCompletionResponse = (AnthropicChatCompletionResponse) responseDTO;
    var chatRespFirstChoice = chatCompletionResponse.content().stream()
        .filter(x -> "text".equals(x.type()) && Strings.isNotBlank(x.text())).findFirst();
    return new TextGenerationResponse(chatRespFirstChoice.map(Content::text).orElse(null),
                                      null, null);
  }

  @Override
  public TokenUsage mapTokenUsageFromResponse(TextResponseDTO responseDTO) {
    var chatCompletionResponse = (AnthropicChatCompletionResponse) responseDTO;
    var chatRespUsage = chatCompletionResponse.usage();

    return new TokenUsage(chatRespUsage.inputTokens(), chatRespUsage.outputTokens(),
                          chatRespUsage.inputTokens() + chatRespUsage.outputTokens());
  }

  @Override
  public AdditionalAttributes mapAdditionalAttributes(TextResponseDTO responseDTO) {
    var chatCompletionResponse = (AnthropicChatCompletionResponse) responseDTO;

    return new AdditionalAttributes(chatCompletionResponse.id(), chatCompletionResponse.model(),
                                    chatCompletionResponse.stopReason());
  }
}
