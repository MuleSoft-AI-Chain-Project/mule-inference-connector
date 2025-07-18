package com.mulesoft.connectors.inference.internal.llmmodels.gpt4all;

import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilities;
import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilitySet;

public enum GPT4ALLModelName implements ModelCapabilities {

  MISTRAL_SMALL_2402("mistral-small-2402", true), QWEN2_1_5B_INSTRUCT("Qwen2-1.5B-Instruct", true);

  private final ModelCapabilitySet capabilities;

  GPT4ALLModelName(String value, boolean textGenerationSupport) {
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
