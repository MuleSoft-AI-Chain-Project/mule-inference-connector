package com.mulesoft.connectors.internal.models;

public enum OpenAIModelName {

    GPT_4_5_PREVIEW("gpt-4.5-preview"),
    O1_MINI("o1-mini"),
    CHATGPT_4O_LATEST("chatgpt-4o-latest"),
    GPT_4O("gpt-4o"),
    GPT_4O_MINI("gpt-4o-mini");

    private final String value;

    OpenAIModelName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
