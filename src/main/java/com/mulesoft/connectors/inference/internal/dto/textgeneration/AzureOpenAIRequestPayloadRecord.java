package com.mulesoft.connectors.inference.internal.dto.textgeneration;

import com.mulesoft.connectors.inference.api.request.ChatPayloadRecord;
import com.mulesoft.connectors.inference.api.request.FunctionDefinitionRecord;

import java.util.List;

public record AzureOpenAIRequestPayloadRecord(List<ChatPayloadRecord>messages,Number maxCompletionTokens,Number temperature,Number topP,boolean stream,List<FunctionDefinitionRecord>tools)implements TextGenerationRequestPayloadDTO{

}
