package com.mulesoft.connectors.inference.internal.llmmodels.deepinfra;

public enum DeepInfraModelName {

  LLAMA_3_8B_INSTRUCT("meta-llama/Meta-Llama-3-8B-Instruct", true);

  private final String value;
  private final boolean textGenerationSupport;

  DeepInfraModelName(String value, boolean textGenerationSupport) {
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
