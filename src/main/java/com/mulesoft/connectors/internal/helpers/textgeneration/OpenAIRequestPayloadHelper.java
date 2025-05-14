package com.mulesoft.connectors.internal.helpers.textgeneration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.internal.dto.ChatPayloadDTO;
import com.mulesoft.connectors.internal.dto.OpenAIRequestPayloadDTO;
import com.mulesoft.connectors.internal.helpers.RequestPayloadHelper;

import java.util.Arrays;
import java.util.List;

public class OpenAIRequestPayloadHelper extends RequestPayloadHelper {

    private static final String[] NO_TEMPERATURE_MODELS = {"o3-mini","o3","o4-mini","o4", "o1", "o1-mini"};

    public OpenAIRequestPayloadHelper(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public OpenAIRequestPayloadDTO buildPayload(TextGenerationConnection connection, List<ChatPayloadDTO> messagesArray) {

        return Arrays.asList(NO_TEMPERATURE_MODELS).contains(connection.getModelName()) ?
                getRequestPayloadDTOWithoutTempAndTopPvalues(connection, messagesArray):
                getOpenAIRequestPayloadDTO(connection, messagesArray);
    }

    private OpenAIRequestPayloadDTO getRequestPayloadDTOWithoutTempAndTopPvalues(TextGenerationConnection connection, List<ChatPayloadDTO> messagesArray) {
        return new OpenAIRequestPayloadDTO(connection.getModelName(),
                messagesArray,
                connection.getMaxTokens(), null, null);
    }

    private OpenAIRequestPayloadDTO getOpenAIRequestPayloadDTO(TextGenerationConnection connection, List<ChatPayloadDTO> messagesArray) {
        return new OpenAIRequestPayloadDTO(connection.getModelName(),
                messagesArray,
                connection.getMaxTokens(),
                connection.getTemperature(),
                connection.getTopP());
    }

}
