package com.mulesoft.connectors.internal.models;

enum MistralModelName {

    mistral_large_latest("mistral-large-latest"),
    mistral_small_latest("mistral-small-latest"),
    open_mistral_nemo("open-mistral-nemo"),
    pixtral_large_latest("pixtral-large-latest");

    private final String value;

    MistralModelName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
