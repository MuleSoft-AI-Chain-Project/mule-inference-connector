package com.mulesoft.connectors.internal.models;

public enum MistralModelName {

    MISTRAL_LARGE_LATEST("mistral-large-latest",true, false),
    MISTRAL_SMALL_LATEST("mistral-small-latest",true, false),
    OPEN_MISTRAL_NEMO("open-mistral-nemo",true, false),
    PIXTRAL_LARGE_LATEST("pixtral-large-latest",true, false),
    MISTRAL_MODERATION_LATEST("mistral-moderation-latest",false,true);

    private final String value;
    private final boolean textGenerationSupport;
    private final boolean moderationSupport;

    MistralModelName(String value, boolean textGenerationSupport, boolean moderationSupport) {
        this.value = value;
        this.textGenerationSupport = textGenerationSupport;
        this.moderationSupport = moderationSupport;
    }

    public boolean isTextGenerationSupport() {
        return textGenerationSupport;
    }

    public boolean isModerationSupport() {
        return moderationSupport;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
