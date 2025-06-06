package com.mulesoft.connectors.inference.internal.llmmodels.gpt4all;

public enum GPT4ALLModelName {

  MISTRAL_SMALL_2402("mistral-small-2402", true), QWEN2_1_5B_INSTRUCT("Qwen2-1.5B-Instruct", true);

  private final String value;
  private final boolean textGenerationSupport;

  GPT4ALLModelName(String value, boolean textGenerationSupport) {
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
