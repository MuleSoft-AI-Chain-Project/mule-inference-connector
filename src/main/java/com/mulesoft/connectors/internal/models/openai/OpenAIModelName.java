package com.mulesoft.connectors.internal.models.openai;

public enum OpenAIModelName {

    GPT_4_5_PREVIEW("gpt-4.5-preview", true, false,false),
    O1_MINI("o1-mini", true, false,false),
    CHATGPT_4O_LATEST("chatgpt-4o-latest", true, false,false),
    GPT_4O("gpt-4o", true, false,false),
    GPT_4O_MINI("gpt-4o-mini", true, false,false),
    TEXT_MODERATION_LATEST_LEGACY("text-moderation-latest",false,true,false),
    OMNI_MODERATION_LATEST("omni-moderation-latest",false,true,false),
    DALL_E_3("dall-e-3",false,false,true),
    DALL_E_2("dall-e-2",false,false,true);

    private final String value;
    private final boolean textGenerationSupport;
    private final boolean moderationSupport;
    private final boolean imageGenerationSupport;

    OpenAIModelName(String value, boolean textGenerationSupport, boolean moderationSupport, boolean imageGenerationSupport) {
        this.value = value;
        this.textGenerationSupport = textGenerationSupport;
        this.moderationSupport = moderationSupport;
        this.imageGenerationSupport = imageGenerationSupport;
    }

    public boolean isTextGenerationSupport() {
        return textGenerationSupport;
    }

    public boolean isModerationSupport() {
        return moderationSupport;
    }

    public boolean isImageGenerationSupport() {
        return imageGenerationSupport;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
