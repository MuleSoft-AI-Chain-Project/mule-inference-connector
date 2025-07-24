package com.mulesoft.connectors.inference.internal.llmmodels.azure;

import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilities;
import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilitySet;

public enum AzureAIFoundryModelName implements ModelCapabilities {

  DEEPSEEK_V3("DeepSeek-V3", true, false), GPT_4O_MINI("gpt-4o-mini", true, false), PHI_3_5_VISION_INSTRUCT(
      "Phi-3.5-vision-instruct", false, true), PHI_4_MULTIMODAL_INSTRUCT("Phi-4-multimodal-instruct", false, true);

  private final ModelCapabilitySet capabilities;

  AzureAIFoundryModelName(String value, boolean textGenerationSupport, boolean visionSupport) {
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
