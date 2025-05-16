package com.mulesoft.connectors.internal.dto.vertexai.google;


import java.util.List;

public record VertexAIGoogleGenerationConfigDTO(List<String> responseModalities, Number temperature, Number topP, Number maxOutputTokens) {

}