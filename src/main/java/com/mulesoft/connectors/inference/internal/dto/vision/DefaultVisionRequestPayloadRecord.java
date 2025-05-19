package com.mulesoft.connectors.inference.internal.dto.vision;

import java.util.List;

public record DefaultVisionRequestPayloadRecord(String model, List<Object> messages, Number maxTokens,
                                                Number temperature, Number topP) implements VisionRequestPayloadDTO {

}
