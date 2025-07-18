package com.mulesoft.connectors.inference.internal.llmmodels.xai;

import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilities;
import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilitySet;

public enum XAIModelName implements ModelCapabilities {

  GROK_2_1212("grok-2-1212", true, false, false), GROK_2_VISION_1212("grok-2-vision-1212", true, false, true), GROK_3_BETA(
      "grok-3-beta", true, false, false), GROK_3_MINI_BETA("grok-3-mini-beta", true, false,
          false), GROK_2_IMAGE("grok-2-image", false, true, false), GROK_VISION_BETA("grok-vision-beta", false, false, true);

  private final ModelCapabilitySet capabilities;

  XAIModelName(String value, boolean textGenerationSupport, boolean imageGenerationSupport, boolean visionSupport) {
    this.capabilities =
        new ModelCapabilitySet(value, textGenerationSupport, false, imageGenerationSupport, visionSupport);
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
