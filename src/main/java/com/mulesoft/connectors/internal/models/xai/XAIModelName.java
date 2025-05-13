package com.mulesoft.connectors.internal.models.xai;

public enum XAIModelName {
    GROK_2_1212("grok-2-1212", true, false),
    GROK_2_VISION_1212("grok-2-vision-1212", true, false),
    GROK_3_BETA("grok-3-beta", true, false),
    GROK_3_MINI_BETA("grok-3-mini-beta", true, false),
    GROK_2_IMAGE("grok-2-image",false,true);

    private final String value;
    private final boolean textGenerationSupport;
    private final boolean imageGenerationSupport;

    XAIModelName(String value, boolean textGenerationSupport, boolean imageGenerationSupport) {
        this.value = value;
        this.textGenerationSupport = textGenerationSupport;
        this.imageGenerationSupport = imageGenerationSupport;
    }

    public boolean isTextGenerationSupport() {
        return textGenerationSupport;
    }

    public boolean isImageGenerationSupport() {
        return imageGenerationSupport;
    }

    @Override
    public String toString() {
        return this.value;
    }
} 