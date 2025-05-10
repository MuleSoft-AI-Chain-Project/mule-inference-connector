package com.mulesoft.connectors.internal.models;

enum AnthropicModelName {

    claude_3_7_sonnet_latest("claude-3-7-sonnet-latest"),
    claude_3_5_haiku_latest("claude-3-5-haiku-latest"),
    claude_3_5_sonnet_latest("claude-3-5-sonnet-latest"),
    claude_3_opus_latest("claude-3-opus-latest");

    private final String value;

    AnthropicModelName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
