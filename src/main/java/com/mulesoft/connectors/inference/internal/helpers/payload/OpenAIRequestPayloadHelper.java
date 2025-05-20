package com.mulesoft.connectors.inference.internal.helpers.payload;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.inference.api.request.ChatPayloadRecord;
import com.mulesoft.connectors.inference.api.request.FunctionDefinitionRecord;
import com.mulesoft.connectors.inference.internal.dto.imagegeneration.OpenAIImageRequestPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.OpenAIRequestPayloadRecord;

import java.util.Arrays;
import java.util.List;

public class OpenAIRequestPayloadHelper extends RequestPayloadHelper {

    private static final String[] NO_TEMPERATURE_MODELS = {"o3-mini","o3","o4-mini","o4", "o1", "o1-mini"};

    public OpenAIRequestPayloadHelper(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public OpenAIRequestPayloadRecord buildPayload(TextGenerationConnection connection, List<ChatPayloadRecord> messagesArray,
                                                   List<FunctionDefinitionRecord> tools) {

        return Arrays.asList(NO_TEMPERATURE_MODELS).contains(connection.getModelName()) ?
                getRequestPayloadDTOWithoutTempAndTopPvalues(connection, messagesArray,tools):
                getOpenAIRequestPayloadDTO(connection, messagesArray,tools);
    }

    public OpenAIImageRequestPayloadRecord createRequestImageGeneration(String model, String prompt) {

        return new OpenAIImageRequestPayloadRecord(model,prompt,"b64_json");
    }

    private OpenAIRequestPayloadRecord getRequestPayloadDTOWithoutTempAndTopPvalues(TextGenerationConnection connection,
                                                                                    List<ChatPayloadRecord> messagesArray, List<FunctionDefinitionRecord> tools) {
        return new OpenAIRequestPayloadRecord(connection.getModelName(),
                messagesArray,
                connection.getMaxTokens(), null, null,tools);
    }

    private OpenAIRequestPayloadRecord getOpenAIRequestPayloadDTO(TextGenerationConnection connection, List<ChatPayloadRecord> messagesArray, List<FunctionDefinitionRecord> tools) {
        return new OpenAIRequestPayloadRecord(connection.getModelName(),
                messagesArray,
                connection.getMaxTokens(),
                connection.getTemperature(),
                connection.getTopP(),tools);
    }

}
