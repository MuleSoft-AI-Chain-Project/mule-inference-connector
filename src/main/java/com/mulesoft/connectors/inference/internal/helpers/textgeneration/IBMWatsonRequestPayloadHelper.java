package com.mulesoft.connectors.inference.internal.helpers.textgeneration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.connection.ibmwatson.IBMWatsonTextGenerationConnection;
import com.mulesoft.connectors.inference.api.input.ChatPayloadDTO;
import com.mulesoft.connectors.inference.api.input.FunctionDefinitionRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.IBMWatsonRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.RequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.helpers.RequestPayloadHelper;

import java.util.List;

public class IBMWatsonRequestPayloadHelper extends RequestPayloadHelper {

    public IBMWatsonRequestPayloadHelper(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public RequestPayloadDTO buildPayload(TextGenerationConnection connection, List<ChatPayloadDTO> messagesArray, List<FunctionDefinitionRecord> tools) {

        IBMWatsonTextGenerationConnection ibmWatsonConnection = (IBMWatsonTextGenerationConnection)connection;

        return new IBMWatsonRequestPayloadDTO(
                ibmWatsonConnection.getModelName(),
                ibmWatsonConnection.getIbmWatsonApiVersion(),
                messagesArray,
                ibmWatsonConnection.getMaxTokens(),
                ibmWatsonConnection.getTemperature(),
                ibmWatsonConnection.getTopP(),tools);
    }
}
