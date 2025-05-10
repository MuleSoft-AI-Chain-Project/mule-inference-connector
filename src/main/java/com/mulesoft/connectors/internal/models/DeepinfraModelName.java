package com.mulesoft.connectors.internal.models;

enum DeepinfraModelName {

    LLAMA_3_8B_INSTRUCT("meta-llama/Meta-Llama-3-8B-Instruct");

    private final String value;

    DeepinfraModelName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
