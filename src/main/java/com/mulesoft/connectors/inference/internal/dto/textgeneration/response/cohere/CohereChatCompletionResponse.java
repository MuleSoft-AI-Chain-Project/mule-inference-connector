package com.mulesoft.connectors.inference.internal.dto.textgeneration.response.cohere;

import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.TextResponseDTO;

public record CohereChatCompletionResponse(String id,Message message,String finishReason,CohereTokenUsage usage)implements TextResponseDTO{}
