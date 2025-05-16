package com.mulesoft.connectors.internal.helpers.textgeneration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.internal.connection.ibmwatson.IBMWatsonTextGenerationConnection;
import com.mulesoft.connectors.internal.dto.*;
import com.mulesoft.connectors.internal.helpers.RequestPayloadHelper;

import java.util.List;

public class IBMWatsonRequestPayloadHelper extends RequestPayloadHelper {

    public IBMWatsonRequestPayloadHelper(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public RequestPayloadDTO buildPayload(TextGenerationConnection connection, List<ChatPayloadDTO> messagesArray,List<FunctionDefinitionRecord> tools) {

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
