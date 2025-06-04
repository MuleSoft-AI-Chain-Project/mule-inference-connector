package com.mulesoft.connectors.inference.internal.llmmodels.ai21labs;

public enum AI21LabsModelName {

  JAMBA_LARGE("jamba-large", true), JAMBA_MINI("jamba-mini", true);

  private final String value;
  private final boolean textGenerationSupport;

  AI21LabsModelName(String value, boolean textGenerationSupport) {
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
