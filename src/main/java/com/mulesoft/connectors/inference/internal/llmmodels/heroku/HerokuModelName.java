package com.mulesoft.connectors.inference.internal.llmmodels.heroku;

import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilities;
import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilitySet;

public enum HerokuModelName implements ModelCapabilities {

  CLAUDE_4_SONNET("claude-4-sonnet", true, false), CLAUDE_3_7_SONNET("claude-3-7-sonnet", true, false), CLAUDE_3_5_SONNET_LATEST(
      "claude-3-5-sonnet-latest", true,
      false), CLAUDE_3_5_HAIKU("claude-3-5-haiku", true,
          false), CLAUDE_3_HAIKU("claude-3-haiku", true, false), STABLE_IMAGE_ULTRA("stable-image-ultra", false, true);

  private final ModelCapabilitySet capabilities;

  HerokuModelName(String value, boolean textGenerationSupport, boolean imageGenerationSupport) {
    this.capabilities =
        new ModelCapabilitySet(value, textGenerationSupport, false, imageGenerationSupport, false);
  }

  @Override
  public ModelCapabilitySet getCapabilities() {
    return this.capabilities;
  }

  @Override
  public String toString() {
    return this.getModelName();
  }
}
