package com.mulesoft.connectors.inference.internal.dto.textgeneration;

import com.mulesoft.connectors.inference.api.request.ChatPayloadRecord;
import com.mulesoft.connectors.inference.api.request.FunctionDefinitionRecord;

import java.util.List;

public record IBMWatsonRequestPayloadRecord(String modelId,String projectId,List<ChatPayloadRecord>messages,Number maxCompletionTokens,Number temperature,Number topP,List<FunctionDefinitionRecord>tools)implements TextGenerationRequestPayloadDTO{

}
