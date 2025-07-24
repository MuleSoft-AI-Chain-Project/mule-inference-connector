package com.mulesoft.connectors.inference.internal.llmmodels.perplexity;

import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilities;
import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilitySet;

public enum PerplexityModelName implements ModelCapabilities {

  SONAR("sonar", true), SONAR_PRO("sonar-pro", true), SONAR_REASONING("sonar-reasoning",
      true), SONAR_REASONING_PRO("sonar-reasoning-pro", true), SONAR_DEEP_RESEARCH("sonar-deep-research", true);

  private final ModelCapabilitySet capabilities;

  PerplexityModelName(String value, boolean textGenerationSupport) {
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
