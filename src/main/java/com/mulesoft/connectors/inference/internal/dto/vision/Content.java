package com.mulesoft.connectors.inference.internal.dto.vision;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ImageContent.class, name = "image"),
    @JsonSubTypes.Type(value = TextContent.class, name = "text"),
    @JsonSubTypes.Type(value = ImageUrlContent.class, name = "image_url")
})
public sealed interface Content

permits ImageContent, ImageUrlContent, TextContent
{

  String type();
}
