package com.mulesoft.connectors.inference.internal.helpers.textgeneration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.AzureOpenAIRequestPayloadDTO;
import com.mulesoft.connectors.inference.api.input.ChatPayloadDTO;
import com.mulesoft.connectors.inference.api.input.FunctionDefinitionRecord;
import com.mulesoft.connectors.inference.internal.helpers.RequestPayloadHelper;

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
