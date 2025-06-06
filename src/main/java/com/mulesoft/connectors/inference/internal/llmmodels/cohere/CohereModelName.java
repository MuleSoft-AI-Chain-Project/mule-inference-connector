package com.mulesoft.connectors.inference.internal.llmmodels.cohere;

public enum CohereModelName {

  COMMAND_R7B_12_2024("command-r7b-12-2024", true), COMMAND_R_PLUS_08_2024("command-r-plus-08-2024", true), COMMAND_R_PLUS(
      "command-r-plus",
      true), COMMAND_R("command-r", true), COMMAND("command", true), COMMAND_R_PLUS_04_2024("command-r-plus-04-2024", true);

  private final String value;
  private final boolean textGenerationSupport;

  CohereModelName(String value, boolean textGenerationSupport) {
    this.value = value;
    this.textGenerationSupport = textGenerationSupport;
  }

  public boolean isTextGenerationSupport() {
    return textGenerationSupport;
  }

  @Override
  public String toString() {
    return this.value;
  }
}
