package com.mulesoft.connectors.inference.internal.helpers.payload;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.dto.imagegeneration.ImageGenerationRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.imagegeneration.XAIImageRequestPayloadRecord;

public class XAIRequestPayloadHelper extends RequestPayloadHelper {

    public XAIRequestPayloadHelper(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    public ImageGenerationRequestPayloadDTO createRequestImageGeneration(String model, String prompt) {

        return new XAIImageRequestPayloadRecord(model,prompt,"b64_json");
    }
}
