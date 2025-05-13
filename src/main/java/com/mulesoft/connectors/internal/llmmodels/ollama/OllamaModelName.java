package com.mulesoft.connectors.internal.llmmodels.ollama;

public enum OllamaModelName {
    MISTRAL("mistral", true, false),
    PHI3("phi3", true, false),
    ORCA_MINI("orca-mini", true, false),
    LLAMA2("llama2", true, false),
    CODE_LLAMA("codellama", true, false),
    TINY_LLAMA("tinyllama", true, false),
    LLAVA_PHI3("llava-phi3",false,true);

    private final String value;
    private final boolean textGenerationSupport;
    private final boolean visionSupport;

    OllamaModelName(String value, boolean textGenerationSupport, boolean visionSupport) {
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