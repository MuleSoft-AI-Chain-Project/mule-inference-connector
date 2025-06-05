package com.mulesoft.connectors.inference.internal.dto.textgeneration.response;

import java.util.List;

public record ChatCompletionResponse(String id,String object,long created,String model,List<Choice>choices,TokenUsage usage,String serviceTier,String systemFingerprint)implements TextResponseDTO{}
