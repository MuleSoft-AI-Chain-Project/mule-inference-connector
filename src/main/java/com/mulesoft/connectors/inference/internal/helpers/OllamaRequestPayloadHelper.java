package com.mulesoft.connectors.inference.internal.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.inference.api.input.ChatPayloadRecord;
import com.mulesoft.connectors.inference.api.input.FunctionDefinitionRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.OllamaRequestPayloadRecord;

import java.util.List;

public class OllamaRequestPayloadHelper extends RequestPayloadHelper {


    public OllamaRequestPayloadHelper(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public OllamaRequestPayloadRecord buildPayload(TextGenerationConnection connection,
                                                   List<ChatPayloadRecord> messagesArray,
                                                   List<FunctionDefinitionRecord> tools) {

        return new OllamaRequestPayloadRecord(connection.getModelName(),
                messagesArray,
                connection.getMaxTokens(),
                connection.getTemperature(),
                connection.getTopP(),
                false,tools);
    }
}
