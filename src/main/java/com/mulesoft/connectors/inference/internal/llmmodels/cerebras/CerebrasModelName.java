package com.mulesoft.connectors.inference.internal.llmmodels.cerebras;

import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilities;
import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilitySet;

public enum CerebrasModelName implements ModelCapabilities {

  LLAMA3_1_8B("llama3.1-8b", true), LLAMA3_1_70B("llama3.1-70b", true);

  private final ModelCapabilitySet capabilities;

  CerebrasModelName(String value, boolean textGenerationSupport) {
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
