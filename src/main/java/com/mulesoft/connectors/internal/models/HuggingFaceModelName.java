package com.mulesoft.connectors.internal.models;

enum HuggingFaceModelName {
    TII_UAE_FALCON_7B_INSTRUCT("tiiuae/falcon-7b-instruct"),
    PHI3("microsoft/Phi-3.5-mini-instruct"),
    MISTRAL_7B_INSTRUCT_V03("mistralai/Mistral-7B-Instruct-v0.3"),
    TINY_LLAMA("TinyLlama/TinyLlama-1.1B-Chat-v1.0");

    private final String value;

    HuggingFaceModelName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
