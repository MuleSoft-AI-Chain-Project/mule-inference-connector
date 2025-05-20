package com.mulesoft.connectors.inference.internal.helpers.payload;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.api.request.ChatPayloadRecord;
import com.mulesoft.connectors.inference.api.request.FunctionDefinitionRecord;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.OllamaRequestPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.vision.DefaultVisionRequestPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.vision.OllamaMessageRecord;

import java.io.IOException;
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

    @Override
    public DefaultVisionRequestPayloadRecord createRequestImageURL(TextGenerationConnection connection, String prompt, String imageUrl) throws IOException {

        return new DefaultVisionRequestPayloadRecord(connection.getModelName(),
                List.of(new OllamaMessageRecord("user", prompt,List.of(imageUrl))),
                connection.getMaxTokens(),
                connection.getTemperature(),
                connection.getTopP());
    }
}
