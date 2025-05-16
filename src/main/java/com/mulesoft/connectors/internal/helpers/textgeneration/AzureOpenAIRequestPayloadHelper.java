package com.mulesoft.connectors.internal.helpers.textgeneration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.internal.dto.AzureOpenAIRequestPayloadDTO;
import com.mulesoft.connectors.internal.dto.ChatPayloadDTO;
import com.mulesoft.connectors.internal.dto.FunctionDefinitionRecord;
import com.mulesoft.connectors.internal.helpers.RequestPayloadHelper;

import java.util.List;

public class AzureOpenAIRequestPayloadHelper extends RequestPayloadHelper {


    public AzureOpenAIRequestPayloadHelper(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public AzureOpenAIRequestPayloadDTO buildPayload(TextGenerationConnection connection,
                                                     List<ChatPayloadDTO> messagesArray,
                                                     List<FunctionDefinitionRecord> tools) {

        return new AzureOpenAIRequestPayloadDTO(
                messagesArray,
                connection.getMaxTokens(),
                connection.getTemperature(),
                connection.getTopP(),
                false, tools);
    }
}
