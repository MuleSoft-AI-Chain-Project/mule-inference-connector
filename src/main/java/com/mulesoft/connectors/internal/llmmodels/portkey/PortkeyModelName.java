package com.mulesoft.connectors.internal.llmmodels.portkey;

public enum PortkeyModelName {
    GPT_4O("gpt-4o", true, false),
    CHATGPT_4O_LATEST("chatgpt-4o-latest", true, false),
    GPT_4O_MINI("gpt-4o-mini", true, false),
    MISTRAL_LARGE_LATEST("mistral-large-latest", true, false),
    MISTRAL_SMALL_LATEST("mistral-small-latest", true, false),
    GPT_4_TURBO("gpt-4-turbo", true, false),
    GPT_4_TURBO_PREVIEW("gpt-4-turbo-preview", true, false),
    GPT_4("gpt-4", true, false),
    GPT_3_5_TURBO("gpt-3.5-turbo", true, false),
    GPT_4_VISION_PREVIEW("gpt-4-vision-preview",false,true);

    private final String value;
    private final boolean textGenerationSupport;
    private final boolean visionSupport;

    PortkeyModelName(String value, boolean textGenerationSupport, boolean visionSupport) {
        this.value = value;
        this.textGenerationSupport = textGenerationSupport;
        this.visionSupport = visionSupport;
    }

    public boolean isTextGenerationSupport() {
        return textGenerationSupport;
    }

    public boolean isVisionSupport() {
        return visionSupport;
    }

    @Override
    public String toString() {
        return this.value;
    }
} 