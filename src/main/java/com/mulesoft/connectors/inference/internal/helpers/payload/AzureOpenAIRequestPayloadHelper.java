package com.mulesoft.connectors.inference.internal.helpers.payload;

import com.mulesoft.connectors.inference.api.request.ChatPayloadRecord;
import com.mulesoft.connectors.inference.api.request.FunctionDefinitionRecord;
import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.connection.types.azure.AzureOpenAITextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.AzureOpenAIRequestPayloadRecord;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AzureOpenAIRequestPayloadHelper extends RequestPayloadHelper {


  public AzureOpenAIRequestPayloadHelper(ObjectMapper objectMapper) {
    super(objectMapper);
  }

  @Override
  public AzureOpenAIRequestPayloadRecord buildPayload(TextGenerationConnection connection,
                                                      List<ChatPayloadRecord> messagesArray,
                                                      List<FunctionDefinitionRecord> tools) {

    return new AzureOpenAIRequestPayloadRecord(
                                               messagesArray,
                                               connection.getMaxTokens(),
                                               connection.getTemperature(),
                                               connection.getTopP(),
                                               ((AzureOpenAITextGenerationConnection) connection).getAzureOpenaiUser(),
                                               false, tools);
  }
}
