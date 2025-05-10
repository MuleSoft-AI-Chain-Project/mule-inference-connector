package com.mulesoft.connectors.internal.models;

enum XinferenceModelName {
    CHATGLM3_6("chatglm3-6b"),
    Qwen25_72B_Instruct("Qwen2.5-72B-Instruct"),
    Qwen25_32B_Instruct("Qwen2.5-32B-Instruct"),
    Qwen25_Coder_7B_Instruct("Qwen2.5-Coder-7B-Instruct"),
    glm_4_9b_chat("glm-4-9b-chat");

    private final String value;

    XinferenceModelName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
