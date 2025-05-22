package com.mulesoft.connectors.inference.internal.dto.moderation;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ModerationRequestPayloadRecord(Object input, String model) {}