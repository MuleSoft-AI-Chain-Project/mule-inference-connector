package com.mulesoft.connectors.internal.helpers.textgeneration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.internal.dto.ChatPayloadDTO;
import com.mulesoft.connectors.internal.dto.FunctionDefinitionRecord;
import com.mulesoft.connectors.internal.dto.OpenAIRequestPayloadDTO;
import com.mulesoft.connectors.internal.helpers.RequestPayloadHelper;

import java.util.List;

public class GroqRequestPayloadHelper extends RequestPayloadHelper {


    public GroqRequestPayloadHelper(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public OpenAIRequestPayloadDTO buildPayload(TextGenerationConnection connection,
                                                List<ChatPayloadDTO> messagesArray,
                                                List<FunctionDefinitionRecord> tools) {

        return new OpenAIRequestPayloadDTO(connection.getModelName(),
                messagesArray,
                connection.getMaxTokens(),
                connection.getTemperature(),
                connection.getTopP(), tools);
    }
}
