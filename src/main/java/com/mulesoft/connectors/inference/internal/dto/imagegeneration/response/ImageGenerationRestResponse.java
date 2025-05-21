package com.mulesoft.connectors.inference.internal.dto.imagegeneration.response;

import java.util.List;

public record ImageGenerationRestResponse(
    Long created,
    List<ImageData> data
) {}

