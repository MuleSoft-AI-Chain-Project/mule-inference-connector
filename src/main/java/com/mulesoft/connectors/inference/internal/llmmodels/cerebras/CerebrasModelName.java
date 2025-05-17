package com.mulesoft.connectors.inference.internal.llmmodels.cerebras;

public enum CerebrasModelName {
    LLAMA3_1_8B("llama3.1-8b", true),
    LLAMA3_1_70B("llama3.1-70b", true);

    private final String value;
    private final boolean textGenerationSupport;

    CerebrasModelName(String value, boolean textGenerationSupport) {
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