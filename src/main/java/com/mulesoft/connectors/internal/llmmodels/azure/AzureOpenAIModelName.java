package com.mulesoft.connectors.internal.llmmodels.azure;

public enum AzureOpenAIModelName {
    //The Model is not specified in the Azure OpenAI API but rather as part of the deployment configuration. In an ideal world we wouldn't need to specify a mdoel when using Azur OpenAI.
    AZURE_OPENAI("azure-openai", true);

    private final String value;
    private final boolean textGenerationSupport;

    AzureOpenAIModelName(String value, boolean textGenerationSupport) {
        this.value = value;
        this.textGenerationSupport = textGenerationSupport;
    }

    public boolean isTextGenerationSupport() {
        return textGenerationSupport;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
