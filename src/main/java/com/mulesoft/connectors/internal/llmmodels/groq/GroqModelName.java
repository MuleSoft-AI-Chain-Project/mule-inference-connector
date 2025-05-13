package com.mulesoft.connectors.internal.llmmodels.groq;

public enum GroqModelName {
    MIXTRAL_8X7B_32768("mixtral-8x7b-32768", true, false),
    LLAMA_3_2_3B_PREVIEW("llama-3.2-3b-preview", true, false),
    LLAMA_3_70B_8192("llama3-70b-8192", true, false),
    LLAMA_3_2_90B_VISION_PREVIEW("llama-3.2-90b-vision-preview", true, true),
    LLAMA_3_2_11B_TEXT_PREVIEW("llama-3.2-11b-text-preview", true, false),
    LLAMA_3_2_1B_PREVIEW("llama-3.2-1b-preview", true, false),
    GEMMA2_9B_IT("gemma2-9b-it", true, false),
    LLAMA3_GROQ_8B_8192_TOOL_USE_PREVIEW("llama3-groq-8b-8192-tool-use-preview", true, false),
    LLAVA_V1_5_7B_4096_PREVIEW("llava-v1.5-7b-4096-preview", true, false),
    LLAMA_3_2_11B_VISION_PREVIEW("llama-3.2-11b-vision-preview", true, true),
    LLAMA_3_2_90B_TEXT_PREVIEW("llama-3.2-90b-text-preview", true, false),
    LLAMA_3_1_8B_INSTANT("llama-3.1-8b-instant", true, false),
    LLAMA3_8B_8192("llama3-8b-8192", true, false),
    LLAMA3_GROQ_70B_8192_TOOL_USE_PREVIEW("llama3-groq-70b-8192-tool-use-preview", true, false),
    LLAMA_3_1_70B_VERSATILE("llama-3.1-70b-versatile", true, false),
    GEMMA_7B_IT("gemma-7b-it", true, false),
    LLAMA_GUARD_3_8B("llama-guard-3-8b", true, false);

    private final String value;
    private final boolean textGenerationSupport;
    private final boolean visionSupport;

    GroqModelName(String value, boolean textGenerationSupport, boolean visionSupport) {
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