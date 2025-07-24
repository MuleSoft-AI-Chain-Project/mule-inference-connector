package com.mulesoft.connectors.inference.internal.llmmodels.nvidia;

import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilities;
import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilitySet;

public enum NvidiaModelName implements ModelCapabilities {

  MISTRAL_7B_INSTRUCT_V0_3("mistralai/mistral-7b-instruct-v0.3", true), AI_YI_LARGE("01-ai/yi-large", true);

  private final ModelCapabilitySet capabilities;

  NvidiaModelName(String value, boolean textGenerationSupport) {
    this.capabilities =
        new ModelCapabilitySet(value, textGenerationSupport, false, false, false);
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
