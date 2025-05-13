package com.mulesoft.connectors.internal.llmmodels.fireworks;

public enum FireworksModelName {
    LLAMA_V3P1_405B_INSTRUCT("accounts/fireworks/models/llama-v3p1-405b-instruct", true);

    private final String value;
    private final boolean textGenerationSupport;

    FireworksModelName(String value, boolean textGenerationSupport) {
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