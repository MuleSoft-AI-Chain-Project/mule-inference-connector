package com.mulesoft.connectors.internal.models;

enum LMSTUDIOModelName {
    //The Model is not specified in the Azure OpenAI API but rather as part of the deployment configuration. In an ideal world we wouldn't need to specify a mdoel when using Azur OpenAI.
    granite_3_0_2b_instruct("granite-3.0-2b-instruct"),
    mistral_nemo("mistral-nemo"),
    ;

    private final String value;

    LMSTUDIOModelName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
