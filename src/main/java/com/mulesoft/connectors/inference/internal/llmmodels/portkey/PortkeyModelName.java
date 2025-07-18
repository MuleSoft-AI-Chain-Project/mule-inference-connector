package com.mulesoft.connectors.inference.internal.llmmodels.portkey;

import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilities;
import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilitySet;

public enum PortkeyModelName implements ModelCapabilities {

  GPT_4O("gpt-4o", true, false), CHATGPT_4O_LATEST("chatgpt-4o-latest", true, false), GPT_4O_MINI("gpt-4o-mini", true,
      false), MISTRAL_LARGE_LATEST("mistral-large-latest", true, false), MISTRAL_SMALL_LATEST("mistral-small-latest", true,
          false), GPT_4_TURBO("gpt-4-turbo", true, false), GPT_4_TURBO_PREVIEW("gpt-4-turbo-preview", true, false), GPT_4("gpt-4",
              true,
              false), GPT_3_5_TURBO("gpt-3.5-turbo", true, false), GPT_4_VISION_PREVIEW("gpt-4-vision-preview", false, true);

  private final ModelCapabilitySet capabilities;

  PortkeyModelName(String value, boolean textGenerationSupport, boolean visionSupport) {
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
