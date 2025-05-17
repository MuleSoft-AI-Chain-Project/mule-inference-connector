package com.mulesoft.connectors.inference.internal.helpers.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.dto.imagegeneration.DefaultImageRequestPayloadRecord;

public class StabilityAIRequestPayloadHelper extends RequestPayloadHelper{

    public StabilityAIRequestPayloadHelper(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public DefaultImageRequestPayloadRecord createRequestImageGeneration(String model, String prompt) {
        return new DefaultImageRequestPayloadRecord(prompt,null);
    }
}
