package com.mulesoft.connectors.internal.models.huggingface;

public enum HuggingFaceModelName {
    TII_UAE_FALCON_7B_INSTRUCT("tiiuae/falcon-7b-instruct", true, false),
    PHI3("microsoft/Phi-3.5-mini-instruct", true, false),
    MISTRAL_7B_INSTRUCT_V03("mistralai/Mistral-7B-Instruct-v0.3", true, false),
    TINY_LLAMA("TinyLlama/TinyLlama-1.1B-Chat-v1.0", true, false),
    BLACK_FOREST_LABS_FLUX_1_DEV("black-forest-labs/FLUX.1-dev",false, true),
    STABILITYAI_STABLE_DIFFUSION_35_LARGE("stabilityai/stable-diffusion-3.5-large",false, true),
    IONET_OFFICIAL_BC8_ALPHA("ionet-official/bc8-alpha",false, true);

    private final String value;
    private final boolean textGenerationSupport;
    private final boolean imageGenerationSupport;

    HuggingFaceModelName(String value, boolean textGenerationSupport, boolean imageGenerationSupport) {
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