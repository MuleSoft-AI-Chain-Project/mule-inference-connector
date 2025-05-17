package com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.google;

import java.util.List;

public record UserContentRecord(String role, List<PartRecord> parts) {
}
