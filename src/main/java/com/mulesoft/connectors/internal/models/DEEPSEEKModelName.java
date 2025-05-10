package com.mulesoft.connectors.internal.models;

enum DEEPSEEKModelName {
    deepseek_chat("deepseek-chat");

    private final String value;

    DEEPSEEKModelName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
