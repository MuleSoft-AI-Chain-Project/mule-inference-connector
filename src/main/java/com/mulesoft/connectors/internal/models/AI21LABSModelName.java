package com.mulesoft.connectors.internal.models;

enum AI21LABSModelName {

    jamba_large("jamba-large"),
    jamba_mini("jamba-mini");

    private final String value;

    AI21LABSModelName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
