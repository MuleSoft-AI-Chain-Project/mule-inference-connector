package com.mulesoft.connectors.internal.llmmodels.together;

public enum TogetherModelName {

    LLAMA_3_1_8B_INSTRUCT_TURBO("meta-llama/Meta-Llama-3.1-8B-Instruct-Turbo", true);

    private final String value;
    private final boolean textGenerationSupport;

    TogetherModelName(String value, boolean textGenerationSupport) {
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