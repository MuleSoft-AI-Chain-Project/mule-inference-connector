package com.mulesoft.connectors.inference.internal.llmmodels.zhipuai;

import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilities;
import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilitySet;

public enum ChatGLMModelName implements ModelCapabilities {

  GLM_4_PLUS("glm-4-plus", true), GLM_4_0520("glm-4-0520", true);

  private final ModelCapabilitySet capabilities;

  ChatGLMModelName(String value, boolean textGenerationSupport) {
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
