package com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.google;

import java.util.List;

public record VertexAIGoogleGenerationConfigRecord(List<String>responseModalities,Number temperature,Number topP,Number maxOutputTokens){

}
