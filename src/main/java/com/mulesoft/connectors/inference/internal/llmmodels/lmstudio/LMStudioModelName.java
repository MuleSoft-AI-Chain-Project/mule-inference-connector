package com.mulesoft.connectors.inference.internal.llmmodels.lmstudio;

public enum LMStudioModelName {

  // The Model is not specified in the Azure OpenAI API but rather as part of the deployment configuration. In an ideal world we
  // wouldn't need to specify a mdoel when using Azur OpenAI.
  GRANITE_3_0_2B_INSTRUCT("granite-3.0-2b-instruct", true), MISTRAL_NEMO("mistral-nemo", true);

  private final String value;
  private final boolean textGenerationSupport;

  LMStudioModelName(String value, boolean textGenerationSupport) {
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
