package com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.google;

import com.mulesoft.connectors.inference.internal.dto.textgeneration.TextGenerationRequestPayloadDTO;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)public record VertexAIGoogleChatPayloadRecord(List<UserContentRecord>contents,SystemInstructionRecord systemInstruction,VertexAIGoogleGenerationConfigRecord generationConfig,List<String>safetySettings,List<String>tools)implements TextGenerationRequestPayloadDTO{}
