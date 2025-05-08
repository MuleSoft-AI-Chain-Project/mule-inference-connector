package com.mulesoft.connectors.internal.models;

enum AzureAIFoundryModelName {

    DeepSeek_V3("DeepSeek-V3"),
    gpt_4o_mini("gpt-4o-mini");

    private final String value;

    AzureAIFoundryModelName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
