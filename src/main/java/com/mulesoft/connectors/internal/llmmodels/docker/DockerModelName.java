package com.mulesoft.connectors.internal.llmmodels.docker;

public enum DockerModelName {
    AI_DEEPSEEK_R1_DISTILL_LLAMA("ai/deepseek-r1-distill-llama", true),
    AI_GEMMA3("ai/gemma3", true),
    AI_LLAMA3_3("ai/llama3.3", true),
    AI_MISTRAL("ai/mistral", true),
    AI_MISTRAL_NEMO("ai/mistral-nemo", true),
    AI_PHI4("ai/phi4", true);

    private final String value;
    private final boolean textGenerationSupport;

    DockerModelName(String value, boolean textGenerationSupport) {
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