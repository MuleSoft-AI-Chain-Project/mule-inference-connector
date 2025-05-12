package com.mulesoft.connectors.internal.models.nvidia;

public enum NVidiaModelName {
    MISTRAL_7B_INSTRUCT_V0_3("mistralai/mistral-7b-instruct-v0.3", true),
    AI_YI_LARGE("01-ai/yi-large", true);

    private final String value;
    private final boolean textGenerationSupport;

    NVidiaModelName(String value, boolean textGenerationSupport) {
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