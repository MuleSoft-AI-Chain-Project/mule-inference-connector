package com.mulesoft.connectors.internal.models;

enum CHATGLMModelName {
    glm_4_plus("glm-4-plus"),
    glm_4_0520("glm-4-0520"),
    ;

    private final String value;

    CHATGLMModelName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
