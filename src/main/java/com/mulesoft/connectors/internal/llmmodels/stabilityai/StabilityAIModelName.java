package com.mulesoft.connectors.internal.llmmodels.stabilityai;

public enum StabilityAIModelName {

    SD3_5_LARGE("sd3.5-large", true),
    SD3_MEDIUM("sd3-medium", true),
    SD3_5_LARGE_TURBO("sd3-large-turbo", true);

    private final String value;
    private final boolean imageGenerationSupport;

    StabilityAIModelName(String value, boolean imageGenerationSupport) {
        this.value = value;

        this.imageGenerationSupport = imageGenerationSupport;
    }

    public boolean isImageGenerationSupport() {
        return imageGenerationSupport;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
