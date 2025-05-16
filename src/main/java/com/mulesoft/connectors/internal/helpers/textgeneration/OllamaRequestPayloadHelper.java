package com.mulesoft.connectors.internal.helpers.textgeneration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.internal.dto.ChatPayloadDTO;
import com.mulesoft.connectors.internal.dto.FunctionDefinitionRecord;
import com.mulesoft.connectors.internal.dto.OllamaRequestPayloadDTO;
import com.mulesoft.connectors.internal.helpers.RequestPayloadHelper;

import java.util.List;

public class OllamaRequestPayloadHelper extends RequestPayloadHelper {


    public OllamaRequestPayloadHelper(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public OllamaRequestPayloadDTO buildPayload(TextGenerationConnection connection,
                                                List<ChatPayloadDTO> messagesArray,
                                                List<FunctionDefinitionRecord> tools) {

        return new OllamaRequestPayloadDTO(connection.getModelName(),
                messagesArray,
                connection.getMaxTokens(),
                connection.getTemperature(),
                connection.getTopP(),
                false,tools);
    }
}
