package com.mulesoft.connectors.inference.internal.llmmodels.cohere;

import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilities;
import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilitySet;

public enum CohereModelName implements ModelCapabilities {

  COMMAND_R7B_12_2024("command-r7b-12-2024", true), COMMAND_R_PLUS_08_2024("command-r-plus-08-2024", true), COMMAND_R_PLUS(
      "command-r-plus",
      true), COMMAND_R("command-r", true), COMMAND("command", true), COMMAND_R_PLUS_04_2024("command-r-plus-04-2024", true);

  private final ModelCapabilitySet capabilities;

  CohereModelName(String value, boolean textGenerationSupport) {
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
