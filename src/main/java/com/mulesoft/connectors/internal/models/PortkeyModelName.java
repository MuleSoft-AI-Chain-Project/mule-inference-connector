package com.mulesoft.connectors.internal.models;

enum PortkeyModelName {
    GPT_4O("gpt-4o"),
    CHATGPT_4O_LATEST("chatgpt-4o-latest"),
    GPT_4O_MINI("gpt-4o-mini"),
    MISTRAL_LARGE_LATEST("mistral-large-latest"),
    MISTRAL_SMALL_LATEST("mistral-small-latest"),
    GPT_4_TURBO("gpt-4-turbo"),
    GPT_4_TURBO_PREVIEW("gpt-4-turbo-preview"),
    GPT_4("gpt-4"),
    GPT_3_5_TURBO("gpt-3.5-turbo");

    private final String value;

    PortkeyModelName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
