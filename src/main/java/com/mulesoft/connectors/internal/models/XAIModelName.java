package com.mulesoft.connectors.internal.models;

enum XAIModelName {

    grok_2_1212("grok-2-1212"),
    grok_2_vision_1212("grok-2-vision-1212"),
    grok_3_beta("grok-3-beta"),
    grok_3_mini_beta("grok-3-mini-beta");

    private final String value;

    XAIModelName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
