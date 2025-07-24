package com.mulesoft.connectors.inference.internal.llmmodels.azure;

import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilities;
import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilitySet;

public enum AzureOpenAIModelName implements ModelCapabilities {

  // The Model is not specified in the Azure OpenAI API but rather as part of the deployment configuration. In an ideal world we
  // wouldn't need to specify a mdoel when using Azur OpenAI.
  AZURE_OPENAI("azure-openai", true);

  private final ModelCapabilitySet capabilities;

  AzureOpenAIModelName(String value, boolean textGenerationSupport) {
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
