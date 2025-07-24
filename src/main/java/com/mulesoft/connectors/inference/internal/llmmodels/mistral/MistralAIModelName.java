package com.mulesoft.connectors.inference.internal.llmmodels.mistral;

import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilities;
import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilitySet;

public enum MistralAIModelName implements ModelCapabilities {

  MISTRAL_LARGE_LATEST("mistral-large-latest", true, false, false), MISTRAL_MODERATION_LATEST("mistral-moderation-latest", false,
      true, false), MISTRAL_SMALL_LATEST("mistral-small-latest", true, false, true), OPEN_MISTRAL_NEMO("open-mistral-nemo", true,
          false, false), PIXTRAL_12B_LATEST("pixtral-12b-latest", false, false,
              true), PIXTRAL_LARGE_LATEST("pixtral-large-latest", true, false, true);

  private final ModelCapabilitySet capabilities;

  MistralAIModelName(String value, boolean textGenerationSupport, boolean moderationSupport, boolean visionSupport) {
    this.capabilities =
        new ModelCapabilitySet(value, textGenerationSupport, moderationSupport, false, visionSupport);
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
