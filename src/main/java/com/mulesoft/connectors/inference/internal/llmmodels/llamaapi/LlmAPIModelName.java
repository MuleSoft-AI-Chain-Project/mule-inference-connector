package com.mulesoft.connectors.inference.internal.llmmodels.llamaapi;

import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilities;
import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilitySet;

public enum LlmAPIModelName implements ModelCapabilities {

  // only using chat model types.
  LLAMA3_1_70B("llama3.1-70b", true);

  private final ModelCapabilitySet capabilities;

  LlmAPIModelName(String value, boolean textGenerationSupport) {
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
