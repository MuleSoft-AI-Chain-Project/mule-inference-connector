package com.mulesoft.connectors.internal.llmmodels.deepseek;

public enum DeepseekModelName {
    DEEPSEEK_CHAT("deepseek-chat", true);

    private final String value;
    private final boolean textGenerationSupport;

    DeepseekModelName(String value, boolean textGenerationSupport) {
        this.value = value;
        this.textGenerationSupport = textGenerationSupport;
    }

    public boolean isTextGenerationSupport() {
        return textGenerationSupport;
    }

    @Override
    public String toString() {
        return this.value;
    }
} 