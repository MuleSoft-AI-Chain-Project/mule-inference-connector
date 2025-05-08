package com.mulesoft.connectors.internal.models;

enum GithubModelName {
    GPT_4O("gpt-4o"),
    PHI_3_5_MOE_INSTRUCT("Phi-3.5-MoE-instruct"),
    MISTRAL_LARGE("Mistral-large"),
    MISTRAL_SMALL("Mistral-small"),
    GPT_4_TURBO("gpt-4-turbo"),
    AI21_JAMBA_1_5_LARGE("AI21-Jamba-1.5-Large"),
    COHERE_COMMAND_R("Cohere-command-r");

    private final String value;

    GithubModelName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
