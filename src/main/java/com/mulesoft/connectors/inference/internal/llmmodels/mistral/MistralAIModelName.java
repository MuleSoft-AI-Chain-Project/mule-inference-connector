package com.mulesoft.connectors.inference.internal.llmmodels.mistral;

public enum MistralAIModelName {

  MISTRAL_LARGE_LATEST("mistral-large-latest", true, false, false), MISTRAL_MODERATION_LATEST("mistral-moderation-latest", false,
      true, false), MISTRAL_SMALL_LATEST("mistral-small-latest", true, false, true), OPEN_MISTRAL_NEMO("open-mistral-nemo", true,
          false, false), PIXTRAL_12B_LATEST("pixtral-12b-latest", false, false,
              true), PIXTRAL_LARGE_LATEST("pixtral-large-latest", true, false, true);


  private final String value;
  private final boolean textGenerationSupport;
  private final boolean moderationSupport;
  private final boolean visionSupport;

  MistralAIModelName(String value, boolean textGenerationSupport, boolean moderationSupport, boolean visionSupport) {
    this.value = value;
    this.textGenerationSupport = textGenerationSupport;
    this.moderationSupport = moderationSupport;
    this.visionSupport = visionSupport;
  }

  public boolean isTextGenerationSupport() {
    return textGenerationSupport;
  }

  public boolean isModerationSupport() {
    return moderationSupport;
  }

  public boolean isVisionSupport() {
    return visionSupport;
  }

  @Override
  public String toString() {
    return this.value;
  }
}
