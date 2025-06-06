package com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.google;

import com.mulesoft.connectors.inference.internal.dto.textgeneration.TextGenerationRequestPayloadDTO;

import java.util.List;

public record VertexAIGoogleChatPayloadRecord(List<UserContentRecord>contents,SystemInstructionRecord systemInstruction,VertexAIGoogleGenerationConfigRecord generationConfig,List<String>safetySettings,List<String>tools)implements TextGenerationRequestPayloadDTO{}
