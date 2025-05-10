package com.mulesoft.connectors.internal.models;

enum CerebrasModelName {
    LLAMA3_1_8B("llama3.1-8b"), LLAMA3_1_70B("llama3.1-70b");

    private final String value;

    CerebrasModelName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
