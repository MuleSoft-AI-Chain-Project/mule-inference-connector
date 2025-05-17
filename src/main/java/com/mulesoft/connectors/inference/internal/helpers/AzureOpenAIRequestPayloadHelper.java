package com.mulesoft.connectors.inference.internal.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.AzureOpenAIRequestPayloadRecord;
import com.mulesoft.connectors.inference.api.input.ChatPayloadRecord;
import com.mulesoft.connectors.inference.api.input.FunctionDefinitionRecord;

import java.util.List;

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
                false, tools);
    }
}
