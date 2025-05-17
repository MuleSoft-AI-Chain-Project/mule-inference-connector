package com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.google;


import java.util.List;

public record SystemInstructionRecord(List<PartRecord> parts) {
}