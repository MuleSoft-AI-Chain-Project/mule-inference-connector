package com.mulesoft.connectors.internal.models;

enum TogetherModelName {

    LLAMA_3_1_8B_INSTRUCT_TURBO("meta-llama/Meta-Llama-3.1-8B-Instruct-Turbo");

    private final String value;

    TogetherModelName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
