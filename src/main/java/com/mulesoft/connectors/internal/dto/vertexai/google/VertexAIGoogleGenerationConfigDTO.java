package com.mulesoft.connectors.internal.dto.vertexai.google;


public record VertexAIGoogleGenerationConfigDTO(String[] responseModalities, Number temperature, Number topP, Number maxOutputTokens) {
}