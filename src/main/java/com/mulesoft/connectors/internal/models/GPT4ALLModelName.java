package com.mulesoft.connectors.internal.models;

enum GPT4ALLModelName {
    //The Model is not specified in the Azure OpenAI API but rather as part of the deployment configuration. In an ideal world we wouldn't need to specify a mdoel when using Azur OpenAI.
    MISTRAL_SMALL_2402("mistral-small-2402"),
    Qwen2_1_5B_Instruct("Qwen2-1.5B-Instruct"),
    ;

    private final String value;

    GPT4ALLModelName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
