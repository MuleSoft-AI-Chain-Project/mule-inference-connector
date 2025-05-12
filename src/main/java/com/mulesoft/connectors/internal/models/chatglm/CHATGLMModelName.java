package com.mulesoft.connectors.internal.models.chatglm;

public enum CHATGLMModelName {
    GLM_4_PLUS("glm-4-plus", true),
    GLM_4_0520("glm-4-0520", true);

    private final String value;
    private final boolean textGenerationSupport;

    CHATGLMModelName(String value, boolean textGenerationSupport) {
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