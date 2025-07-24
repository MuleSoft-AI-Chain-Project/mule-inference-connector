package com.mulesoft.connectors.inference.internal.dto.textgeneration.response.ollama;

import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.TextResponseDTO;

public record OllamaChatCompletionResponse(String model,String createdAt,Message message,String doneReason,boolean done,long totalDuration,long loadDuration,int promptEvalCount,long promptEvalDuration,int evalCount,long evalDuration)implements TextResponseDTO{}


