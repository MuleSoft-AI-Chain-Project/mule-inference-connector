package com.mulesoft.connectors.inference.internal.llmmodels.azure;

public enum AzureAIFoundryModelName {

  DEEPSEEK_V3("DeepSeek-V3", true, false), GPT_4O_MINI("gpt-4o-mini", true, false), PHI_3_5_VISION_INSTRUCT(
      "Phi-3.5-vision-instruct", false, true), PHI_4_MULTIMODAL_INSTRUCT("Phi-4-multimodal-instruct", false, true);

  private final String value;
  private final boolean textGenerationSupport;
  private final boolean visionSupport;

  AzureAIFoundryModelName(String value, boolean textGenerationSupport, boolean visionSupport) {
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
