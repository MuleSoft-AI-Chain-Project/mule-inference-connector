package com.mulesoft.connectors.inference.internal.helpers.payload;

import com.mulesoft.connectors.inference.api.request.ChatPayloadRecord;
import com.mulesoft.connectors.inference.api.request.FunctionDefinitionRecord;
import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.CohereRequestPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.TextGenerationRequestPayloadDTO;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CohereRequestPayloadHelper extends RequestPayloadHelper {

  public CohereRequestPayloadHelper(ObjectMapper objectMapper) {
    super(objectMapper);
  }

  @Override
  public TextGenerationRequestPayloadDTO buildPayload(TextGenerationConnection connection, List<ChatPayloadRecord> messagesArray,
                                                      List<FunctionDefinitionRecord> tools) {
    return new CohereRequestPayloadRecord(connection.getModelName(),
                                          messagesArray,
                                          connection.getMaxTokens(),
                                          connection.getTemperature(),
                                          tools);
  }
}
