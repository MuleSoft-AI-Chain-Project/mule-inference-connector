package com.mulesoft.connectors.internal.models;

enum AzureOpenAIModelName {
    //The Model is not specified in the Azure OpenAI API but rather as part of the deployment configuration. In an ideal world we wouldn't need to specify a mdoel when using Azur OpenAI.
    AZURE_OPENAI("azure-openai");

    private final String value;

    AzureOpenAIModelName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
