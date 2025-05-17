package com.mulesoft.connectors.inference.internal.llmmodels.xinference;

public enum XInferenceModelName {
    CHATGLM3_6("chatglm3-6b", true),
    QWEN25_72B_INSTRUCT("Qwen2.5-72B-Instruct", true),
    QWEN25_32B_INSTRUCT("Qwen2.5-32B-Instruct", true),
    QWEN25_CODER_7B_INSTRUCT("Qwen2.5-Coder-7B-Instruct", true),
    GLM_4_9B_CHAT("glm-4-9b-chat", true);

    private final String value;
    private final boolean textGenerationSupport;

    XInferenceModelName(String value, boolean textGenerationSupport) {
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