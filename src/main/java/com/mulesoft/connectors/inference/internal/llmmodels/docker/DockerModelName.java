package com.mulesoft.connectors.inference.internal.llmmodels.docker;

import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilities;
import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilitySet;

public enum DockerModelName implements ModelCapabilities {

  AI_DEEPSEEK_R1_DISTILL_LLAMA("ai/deepseek-r1-distill-llama", true), AI_GEMMA3("ai/gemma3", true), AI_LLAMA3_3("ai/llama3.3",
      true), AI_MISTRAL("ai/mistral", true), AI_MISTRAL_NEMO("ai/mistral-nemo", true), AI_PHI4("ai/phi4", true);

  private final ModelCapabilitySet capabilities;

  DockerModelName(String value, boolean textGenerationSupport) {
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
