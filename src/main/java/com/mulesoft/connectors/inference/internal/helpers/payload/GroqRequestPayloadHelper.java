package com.mulesoft.connectors.inference.internal.helpers.payload;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.inference.api.request.ChatPayloadRecord;
import com.mulesoft.connectors.inference.api.request.FunctionDefinitionRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.OpenAIRequestPayloadRecord;

import java.util.List;

public class GroqRequestPayloadHelper extends RequestPayloadHelper {


    public GroqRequestPayloadHelper(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public OpenAIRequestPayloadRecord buildPayload(TextGenerationConnection connection,
                                                   List<ChatPayloadRecord> messagesArray,
                                                   List<FunctionDefinitionRecord> tools) {

        return new OpenAIRequestPayloadRecord(connection.getModelName(),
                messagesArray,
                connection.getMaxTokens(),
                connection.getTemperature(),
                connection.getTopP(), tools);
    }
}
