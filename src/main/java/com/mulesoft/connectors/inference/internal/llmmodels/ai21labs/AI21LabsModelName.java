package com.mulesoft.connectors.inference.internal.llmmodels.ai21labs;

import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilities;
import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilitySet;

public enum AI21LabsModelName implements ModelCapabilities {

  JAMBA_LARGE("jamba-large", true), JAMBA_MINI("jamba-mini", true);

  private final ModelCapabilitySet capabilities;

  AI21LabsModelName(String value, boolean textGenerationSupport) {
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
