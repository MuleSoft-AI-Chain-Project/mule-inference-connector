package com.mulesoft.connectors.internal.llmmodels.openai;

public enum OpenAIModelName {

    GPT_4_5_PREVIEW("gpt-4.5-preview", true, false,false,true),
    O1_MINI("o1-mini", true, false,false,false),
    CHATGPT_4O_LATEST("chatgpt-4o-latest", true, false,false,true),
    GPT_4O("gpt-4o", true, false,false,true),
    GPT_4O_MINI("gpt-4o-mini", true, false,false,true),
    TEXT_MODERATION_LATEST_LEGACY("text-moderation-latest",false,true,false, false),
    OMNI_MODERATION_LATEST("omni-moderation-latest",false,true,false, false),
    DALL_E_3("dall-e-3",false,false,true, false),
    DALL_E_2("dall-e-2",false,false,true, false),
    GPT_4_TURBO("gpt-4-turbo",false,false,false,true);

    private final String value;
    private final boolean textGenerationSupport;
    private final boolean moderationSupport;
    private final boolean imageGenerationSupport;
    private final boolean visionSupport;

    OpenAIModelName(String value, boolean textGenerationSupport, boolean moderationSupport, boolean imageGenerationSupport, boolean visionSupport) {
        this.value = value;
        this.textGenerationSupport = textGenerationSupport;
        this.moderationSupport = moderationSupport;
        this.imageGenerationSupport = imageGenerationSupport;
        this.visionSupport = visionSupport;
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

    public boolean isVisionSupport() {
        return visionSupport;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
