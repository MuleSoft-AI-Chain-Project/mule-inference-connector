package com.mulesoft.connectors.inference.internal.helpers.payload;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.AzureOpenAIRequestPayloadRecord;
import com.mulesoft.connectors.inference.api.request.ChatPayloadRecord;
import com.mulesoft.connectors.inference.api.request.FunctionDefinitionRecord;

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
