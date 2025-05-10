package com.mulesoft.connectors.internal.models;

public enum OpenAIModelName {

    GPT_4_5_PREVIEW("gpt-4.5-preview", true, false),
    O1_MINI("o1-mini", true, false),
    CHATGPT_4O_LATEST("chatgpt-4o-latest", true, false),
    GPT_4O("gpt-4o", true, false),
    GPT_4O_MINI("gpt-4o-mini", true, false),
    TEXT_MODERATION_LATEST_LEGACY("text-moderation-latest",false,true),
    OMNI_MODERATION_LATEST("omni-moderation-latest",false,true);

    private final String value;
    private final boolean textGenerationSupport;
    private final boolean moderationSupport;

    OpenAIModelName(String value, boolean textGenerationSupport, boolean moderationSupport) {
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
