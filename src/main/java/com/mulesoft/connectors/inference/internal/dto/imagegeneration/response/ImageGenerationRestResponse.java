package com.mulesoft.connectors.inference.internal.dto.imagegeneration.response;

import java.util.List;

public record ImageGenerationRestResponse(
    long created,
    List<ImageData> data
) {}

