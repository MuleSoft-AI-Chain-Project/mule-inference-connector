package com.mulesoft.connectors.inference.internal.llmmodels.lmstudio;

import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilities;
import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilitySet;

public enum LMStudioModelName implements ModelCapabilities {

  // The Model is not specified in the Azure OpenAI API but rather as part of the deployment configuration. In an ideal world we
  // wouldn't need to specify a mdoel when using Azur OpenAI.
  GRANITE_3_0_2B_INSTRUCT("granite-3.0-2b-instruct", true), MISTRAL_NEMO("mistral-nemo", true);

  private final ModelCapabilitySet capabilities;

  LMStudioModelName(String value, boolean textGenerationSupport) {
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
