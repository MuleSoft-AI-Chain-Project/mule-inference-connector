package com.mulesoft.connectors.inference.internal.dto.vision;

import java.util.List;

public record OllamaMessageRecord(String role, String content, List<String> images) {
}


