package com.mulesoft.connectors.inference.internal.llmmodels.ollama;

import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilities;
import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilitySet;

public enum OllamaModelName implements ModelCapabilities {

  MISTRAL("mistral", true, false), PHI3("phi3", true, false), ORCA_MINI("orca-mini", true, false), LLAMA2("llama2", true,
      false), CODE_LLAMA("codellama", true, false), TINY_LLAMA("tinyllama", true, false), LLAVA_PHI3("llava-phi3", false, true);


  private final ModelCapabilitySet capabilities;

  OllamaModelName(String value, boolean textGenerationSupport, boolean visionSupport) {
    this.capabilities =
        new ModelCapabilitySet(value, textGenerationSupport, false, false, visionSupport);
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
