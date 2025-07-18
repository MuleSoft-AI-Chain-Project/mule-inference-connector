package com.mulesoft.connectors.inference.internal.llmmodels.stabilityai;

import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilities;
import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilitySet;

public enum StabilityAIModelName implements ModelCapabilities {

  SD3_5_LARGE("sd3.5-large", true), SD3_MEDIUM("sd3-medium", true), SD3_5_LARGE_TURBO("sd3-large-turbo", true);

  private final ModelCapabilitySet capabilities;

  StabilityAIModelName(String value, boolean imageGenerationSupport) {
    this.capabilities =
        new ModelCapabilitySet(value, false, false, imageGenerationSupport, false);
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
