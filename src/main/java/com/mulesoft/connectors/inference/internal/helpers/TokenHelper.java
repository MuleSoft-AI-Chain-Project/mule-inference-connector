package com.mulesoft.connectors.inference.internal.helpers;

import com.mulesoft.connectors.inference.api.metadata.TokenUsage;

public class TokenHelper {

    private TokenHelper() {}

    public static TokenUsage parseUsageFromResponse(com.mulesoft.connectors.inference.internal.dto.textgeneration.response.TokenUsage usage) {
        return new TokenUsage(usage.promptTokens(), usage.completionTokens(), usage.totalTokens());
    }
}