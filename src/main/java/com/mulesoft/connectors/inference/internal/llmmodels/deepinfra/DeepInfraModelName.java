package com.mulesoft.connectors.inference.internal.llmmodels.deepinfra;

import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilities;
import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilitySet;

public enum DeepInfraModelName implements ModelCapabilities {

  LLAMA_3_8B_INSTRUCT("meta-llama/Meta-Llama-3-8B-Instruct", true);

  private final ModelCapabilitySet capabilities;

  DeepInfraModelName(String value, boolean textGenerationSupport) {
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
