package com.mulesoft.connectors.internal.models;

enum DockerModelName {
    //The Model is not specified in the Azure OpenAI API but rather as part of the deployment configuration. In an ideal world we wouldn't need to specify a mdoel when using Azur OpenAI.
    ai_deepseek_r1_distill_llama("ai/deepseek-r1-distill-llama"),
    ai_gemma3("ai/gemma3"),
    ai_llama3_3("ai/llama3.3"),
    ai_mistral("ai/mistral"),
    ai_mistral_nemo("ai/mistral-nemo"),
    ai_phi4("ai/phi4"),
    ;

    private final String value;

    DockerModelName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
