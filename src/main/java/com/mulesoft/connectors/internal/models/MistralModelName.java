package com.mulesoft.connectors.internal.models;

public enum MistralModelName {

    MISTRAL_LARGE_LATEST("mistral-large-latest"),
    MISTRAL_SMALL_LATEST("mistral-small-latest"),
    OPEN_MISTRAL_NEMO("open-mistral-nemo"),
    PIXTRAL_LARGE_LATEST("pixtral-large-latest");

    private final String value;

    MistralModelName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
