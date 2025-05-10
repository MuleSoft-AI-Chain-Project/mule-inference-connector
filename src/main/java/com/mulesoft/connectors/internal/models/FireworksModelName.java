package com.mulesoft.connectors.internal.models;

enum FireworksModelName {
    LLAMA_V3P1_405B_INSTRUCT("accounts/fireworks/models/llama-v3p1-405b-instruct");

    private final String value;

    FireworksModelName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
