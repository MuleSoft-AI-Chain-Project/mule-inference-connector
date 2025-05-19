package com.mulesoft.connectors.inference.internal.dto.vision;

import java.util.List;

public record Message(String role,List<Content> content) {
}


