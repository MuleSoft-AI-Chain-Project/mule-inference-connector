package com.mulesoft.connectors.internal.llmmodels.anthropic;

public enum AnthropicModelName {

    CLAUDE_3_7_SONNET_LATEST("claude-3-7-sonnet-latest", true, false),
    CLAUDE_3_5_HAIKU_LATEST("claude-3-5-haiku-latest", true, false),
    CLAUDE_3_5_SONNET_LATEST("claude-3-5-sonnet-latest", true, false),
    CLAUDE_3_OPUS_LATEST("claude-3-opus-latest", true, false),
    CLAUDE_3_7_SONNET_20250219("claude-3-7-sonnet-20250219",false, true),
    CLAUDE_3_5_HAIKU_20241022("claude-3-5-haiku-20241022",false, true),
    CLAUDE_3_5_SONNET_20241022("claude-3-5-sonnet-20241022",false, true),
    CLAUDE_3_OPUS_20240229("claude-3-opus-20240229",false, true);

    private final String value;
    private final boolean textGenerationSupport;
    private final boolean visionSupport;

    AnthropicModelName(String value, boolean textGenerationSupport, boolean visionSupport) {
        this.value = value;
        this.textGenerationSupport = textGenerationSupport;
        this.visionSupport = visionSupport;
    }

    public boolean isTextGenerationSupport() {
        return textGenerationSupport;
    }

    public boolean isVisionSupport() {
        return visionSupport;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
