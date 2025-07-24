package com.mulesoft.connectors.inference.internal.llmmodels.openai;

import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilities;
import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilitySet;

public enum OpenAIModelName implements ModelCapabilities {

  GPT_4_5_PREVIEW("gpt-4.5-preview", true, false, false, true), O1_MINI("o1-mini", true, false, false, false), CHATGPT_4O_LATEST(
      "chatgpt-4o-latest", true, false, false, true), GPT_4O("gpt-4o", true, false, false, true), GPT_4O_MINI("gpt-4o-mini", true,
          false, false, true), TEXT_MODERATION_LATEST_LEGACY("text-moderation-latest", false, true, false,
              false), OMNI_MODERATION_LATEST("omni-moderation-latest", false, true, false, false), DALL_E_3("dall-e-3", false,
                  false, true,
                  false), DALL_E_2("dall-e-2", false, false, true, false), GPT_4_TURBO("gpt-4-turbo", false, false, false, true);

  private final ModelCapabilitySet capabilities;

  OpenAIModelName(String value, boolean textGenerationSupport, boolean moderationSupport, boolean imageGenerationSupport,
                  boolean visionSupport) {
    this.capabilities =
        new ModelCapabilitySet(value, textGenerationSupport, moderationSupport, imageGenerationSupport, visionSupport);
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
