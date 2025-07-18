package com.mulesoft.connectors.inference.internal.llmmodels.together;

import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilities;
import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilitySet;

public enum TogetherModelName implements ModelCapabilities {

  LLAMA_3_1_8B_INSTRUCT_TURBO("meta-llama/Meta-Llama-3.1-8B-Instruct-Turbo", true);

  private final ModelCapabilitySet capabilities;

  TogetherModelName(String value, boolean textGenerationSupport) {
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
