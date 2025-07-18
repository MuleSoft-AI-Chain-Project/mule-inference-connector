package com.mulesoft.connectors.inference.internal.llmmodels.xinference;

import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilities;
import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilitySet;

public enum XInferenceModelName implements ModelCapabilities {

  CHATGLM3_6("chatglm3-6b", true), QWEN25_72B_INSTRUCT("Qwen2.5-72B-Instruct", true), QWEN25_32B_INSTRUCT("Qwen2.5-32B-Instruct",
      true), QWEN25_CODER_7B_INSTRUCT("Qwen2.5-Coder-7B-Instruct", true), GLM_4_9B_CHAT("glm-4-9b-chat", true);

  private final ModelCapabilitySet capabilities;

  XInferenceModelName(String value, boolean textGenerationSupport) {
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
