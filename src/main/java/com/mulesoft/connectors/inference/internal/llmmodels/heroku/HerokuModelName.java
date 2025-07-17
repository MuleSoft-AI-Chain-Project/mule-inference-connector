package com.mulesoft.connectors.inference.internal.llmmodels.heroku;

public enum HerokuModelName {

  CLAUDE_4_SONNET("claude-4-sonnet", true, false), CLAUDE_3_7_SONNET("claude-3-7-sonnet", true, false), CLAUDE_3_5_SONNET_LATEST(
      "claude-3-5-sonnet-latest", true,
      false), CLAUDE_3_5_HAIKU("claude-3-5-haiku", true,
          false), CLAUDE_3_HAIKU("claude-3-haiku", true, false), STABLE_IMAGE_ULTRA("stable-image-ultra", false, true);

  private final String value;
  private final boolean textGenerationSupport;
  private final boolean imageGenerationSupport;

  HerokuModelName(String value, boolean textGenerationSupport, boolean imageGenerationSupport) {
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
