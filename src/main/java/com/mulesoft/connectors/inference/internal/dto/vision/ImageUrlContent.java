package com.mulesoft.connectors.inference.internal.dto.vision;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ImageUrlContent(
    String type,
    @JsonProperty("image_url") ImageUrl imageUrl
) implements Content {
}