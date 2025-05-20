package com.mulesoft.connectors.inference.internal.dto.textgeneration.response;

import java.util.List;

public record Message(
    String role,
    String content,
    String refusal,
    List<String> annotations
) {}
