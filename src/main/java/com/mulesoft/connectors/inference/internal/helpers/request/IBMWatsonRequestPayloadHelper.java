package com.mulesoft.connectors.inference.internal.helpers.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.connection.ibmwatson.IBMWatsonTextGenerationConnection;
import com.mulesoft.connectors.inference.api.request.ChatPayloadRecord;
import com.mulesoft.connectors.inference.api.request.FunctionDefinitionRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.IBMWatsonRequestPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.TextGenerationRequestPayloadDTO;

import java.util.List;

public class IBMWatsonRequestPayloadHelper extends RequestPayloadHelper {

    public IBMWatsonRequestPayloadHelper(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public TextGenerationRequestPayloadDTO buildPayload(TextGenerationConnection connection, List<ChatPayloadRecord> messagesArray, List<FunctionDefinitionRecord> tools) {

        IBMWatsonTextGenerationConnection ibmWatsonConnection = (IBMWatsonTextGenerationConnection)connection;

        return new IBMWatsonRequestPayloadRecord(
                ibmWatsonConnection.getModelName(),
                ibmWatsonConnection.getIbmWatsonApiVersion(),
                messagesArray,
                ibmWatsonConnection.getMaxTokens(),
                ibmWatsonConnection.getTemperature(),
                ibmWatsonConnection.getTopP(),tools);
    }
}
