package com.mulesoft.connectors.inference.internal.llmmodels.deepseek;

import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilities;
import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilitySet;

public enum DeepseekModelName implements ModelCapabilities {

  DEEPSEEK_CHAT("deepseek-chat", true);

  private final ModelCapabilitySet capabilities;

  DeepseekModelName(String value, boolean textGenerationSupport) {
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
