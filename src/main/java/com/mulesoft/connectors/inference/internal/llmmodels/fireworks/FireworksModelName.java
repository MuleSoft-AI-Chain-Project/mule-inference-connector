package com.mulesoft.connectors.inference.internal.llmmodels.fireworks;

import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilities;
import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilitySet;

public enum FireworksModelName implements ModelCapabilities {

  LLAMA_V3P1_405B_INSTRUCT("accounts/fireworks/models/llama-v3p1-405b-instruct", true);

  private final ModelCapabilitySet capabilities;

  FireworksModelName(String value, boolean textGenerationSupport) {
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
