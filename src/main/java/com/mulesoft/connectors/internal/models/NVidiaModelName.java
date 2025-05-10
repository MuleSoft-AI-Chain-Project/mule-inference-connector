package com.mulesoft.connectors.internal.models;

enum NVidiaModelName {
    MISTRAL_7B_INSTRUCT_v0_3("mistralai/mistral-7b-instruct-v0.3"), AI_YI_LARGE("01-ai/yi-large");

    private final String value;

    NVidiaModelName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
