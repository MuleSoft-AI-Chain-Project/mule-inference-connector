package com.mulesoft.connectors.inference.internal.helpers.textgeneration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.inference.api.input.ChatPayloadDTO;
import com.mulesoft.connectors.inference.api.input.FunctionDefinitionRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.OpenAIRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.helpers.RequestPayloadHelper;

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
