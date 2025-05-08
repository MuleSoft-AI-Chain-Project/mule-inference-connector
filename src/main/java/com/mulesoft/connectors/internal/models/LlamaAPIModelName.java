package com.mulesoft.connectors.internal.models;

enum LlamaAPIModelName {
    // only using chat model types.
    llama3_1_70b("llama3.1-70b");


    private final String value;

    LlamaAPIModelName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
