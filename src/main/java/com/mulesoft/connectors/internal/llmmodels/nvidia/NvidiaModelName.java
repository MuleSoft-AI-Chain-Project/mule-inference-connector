package com.mulesoft.connectors.internal.llmmodels.nvidia;

public enum NvidiaModelName {
    MISTRAL_7B_INSTRUCT_V0_3("mistralai/mistral-7b-instruct-v0.3", true),
    AI_YI_LARGE("01-ai/yi-large", true);

    private final String value;
    private final boolean textGenerationSupport;

    NvidiaModelName(String value, boolean textGenerationSupport) {
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