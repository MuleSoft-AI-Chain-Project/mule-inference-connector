package com.mulesoft.connectors.inference.internal.helpers.response.mapper;

import com.mulesoft.connectors.inference.api.metadata.AdditionalAttributes;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.ChatCompletionResponse;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.TextResponseDTO;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Ai21labsResponseMapper extends DefaultResponseMapper {

  public Ai21labsResponseMapper(ObjectMapper objectMapper) {
    super(objectMapper);
  }


  @Override
  public AdditionalAttributes mapAdditionalAttributes(TextResponseDTO responseDTO, String modelName) {
    var chatCompletionResponse = (ChatCompletionResponse) responseDTO;
    var chatRespFirstChoice = chatCompletionResponse.choices().get(0);
    return new AdditionalAttributes(chatCompletionResponse.id(), modelName,
                                    chatRespFirstChoice.finishReason());
  }
}
