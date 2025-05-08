package com.mulesoft.connectors.internal.models;

enum OpenAIModelName {

    gpt_4_5_preview("gpt-4.5-preview"),
    o1_mini("o1-mini"),
    chatgpt_4o_latest("chatgpt-4o-latest"),
    gpt_4o("gpt-4o"),
    gpt_4o_mini("gpt-4o-mini");

    private final String value;

    OpenAIModelName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
