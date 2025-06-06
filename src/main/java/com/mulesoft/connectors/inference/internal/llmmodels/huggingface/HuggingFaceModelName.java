package com.mulesoft.connectors.inference.internal.llmmodels.huggingface;

public enum HuggingFaceModelName {

  TII_UAE_FALCON_7B_INSTRUCT("tiiuae/falcon-7b-instruct", true, false, false), PHI3("microsoft/Phi-3.5-mini-instruct", true,
      false, false), MISTRAL_7B_INSTRUCT_V03("mistralai/Mistral-7B-Instruct-v0.3", true, false, false), TINY_LLAMA(
          "TinyLlama/TinyLlama-1.1B-Chat-v1.0", true, false, false), BLACK_FOREST_LABS_FLUX_1_DEV("black-forest-labs/FLUX.1-dev",
              false, true, false), STABILITYAI_STABLE_DIFFUSION_35_LARGE("stabilityai/stable-diffusion-3.5-large", false, true,
                  false), IONET_OFFICIAL_BC8_ALPHA("ionet-official/bc8-alpha", false, true, false),

  QWEN_QWEN2_5_VL_7B_INSTRUCT("Qwen/Qwen2.5-VL-7B-Instruct", false, false, true), QWEN_QWEN2_VL_7B_INSTRUCT(
      "Qwen/Qwen2-VL-7B-Instruct", false, false, true), GOOGLE_GEMMA_3_27B_IT("google/gemma-3-27b-it", false, false,
          true), META_LLAMA_LLAMA_3_2_11B_VISION_INSTRUCT("meta-llama/Llama-3.2-11B-Vision-Instruct", false, false, true);

  private final String value;
  private final boolean textGenerationSupport;
  private final boolean imageGenerationSupport;
  private final boolean visionSupport;

  HuggingFaceModelName(String value, boolean textGenerationSupport, boolean imageGenerationSupport, boolean visionSupport) {
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
