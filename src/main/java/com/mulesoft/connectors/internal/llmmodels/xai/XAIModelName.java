package com.mulesoft.connectors.internal.llmmodels.xai;

public enum XAIModelName {
    GROK_2_1212("grok-2-1212", true, false, false),
    GROK_2_VISION_1212("grok-2-vision-1212", true, false, true),
    GROK_3_BETA("grok-3-beta", true, false, false),
    GROK_3_MINI_BETA("grok-3-mini-beta", true, false, false),
    GROK_2_IMAGE("grok-2-image",false,true, false),
    GROK_VISION_BETA("grok-vision-beta",false,false, true);

    private final String value;
    private final boolean textGenerationSupport;
    private final boolean imageGenerationSupport;
    private final boolean visionSupport;

    XAIModelName(String value, boolean textGenerationSupport, boolean imageGenerationSupport, boolean visionSupport) {
        this.value = value;
        this.textGenerationSupport = textGenerationSupport;
        this.imageGenerationSupport = imageGenerationSupport;
        this.visionSupport = visionSupport;
    }

    public boolean isTextGenerationSupport() {
        return textGenerationSupport;
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