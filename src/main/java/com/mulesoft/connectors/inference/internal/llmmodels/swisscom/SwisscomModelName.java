package com.mulesoft.connectors.inference.internal.llmmodels.swisscom;

import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilities;
import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilitySet;

public enum SwisscomModelName implements ModelCapabilities {

  META_LLAMA_3_3_70B_INSTRUCT("meta/llama-3.3-70b-instruct", true, false, false, false);

  private final ModelCapabilitySet capabilities;

  SwisscomModelName(String value, boolean textGenerationSupport, boolean moderationSupport, boolean imageGenerationSupport,
                    boolean visionSupport) {
    this.capabilities =
        new ModelCapabilitySet(value, textGenerationSupport, moderationSupport, imageGenerationSupport, visionSupport);
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
