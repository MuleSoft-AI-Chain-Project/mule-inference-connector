package com.mulesoft.connectors.internal.models;

enum GroqModelName {
    MIXTRAL_8x7b_32768("mixtral-8x7b-32768"),
    LLAMA_3_2_3b_PREVIEW("llama-3.2-3b-preview"),
    LLAMA_3_70b_8192("llama3-70b-8192"),
    LLAMA_3_2_90b_VISION_PREVIEW("llama-3.2-90b-vision-preview"),
    LLAMA_3_2_11b_TEXT_PREVIEW("llama-3.2-11b-text-preview"),
    LLAMA_3_2_1b_PREVIEW("llama-3.2-1b-preview"),
    GEMMA2_9b_IT("gemma2-9b-it"),
    LLAMA3_GROQ_8b_8192_TOOL_USE_PREVIEW("llama3-groq-8b-8192-tool-use-preview"),
    LLAVA_V1_5_7b_4096_PREVIEW("llava-v1.5-7b-4096-preview"),
    LLAMA_3_2_11b_VISION_PREVIEW("llama-3.2-11b-vision-preview"),
    LLAMA_3_2_90b_TEXT_PREVIEW("llama-3.2-90b-text-preview"),
    LLAMA_3_1_8b_INSTANT("llama-3.1-8b-instant"),
    LLAMA3_8b_8192("llama3-8b-8192"),
    LLAMA3_GROQ_70b_8192_TOOL_USE_PREVIEW("llama3-groq-70b-8192-tool-use-preview"),
    LLAMA_3_1_70b_VERSATILE("llama-3.1-70b-versatile"),
    GEMMA_7b_IT("gemma-7b-it"),
    LLAMA_GUARD_3_8b("llama-guard-3-8b");

    private final String value;

    GroqModelName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
