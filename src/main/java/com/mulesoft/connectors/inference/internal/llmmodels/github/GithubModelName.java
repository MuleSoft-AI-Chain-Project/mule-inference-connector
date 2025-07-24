package com.mulesoft.connectors.inference.internal.llmmodels.github;

import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilities;
import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilitySet;

public enum GithubModelName implements ModelCapabilities {

  GPT_4O("gpt-4o", true, false), PHI_3_5_MOE_INSTRUCT("Phi-3.5-MoE-instruct", true, false), MISTRAL_LARGE("Mistral-large", true,
      false), MISTRAL_SMALL("Mistral-small", true, false), GPT_4_TURBO("gpt-4-turbo", true, false), AI21_JAMBA_1_5_LARGE(
          "AI21-Jamba-1.5-Large", true, false), COHERE_COMMAND_R("Cohere-command-r", true, false), PHI_3_5_VISION_INSTRUCT(
              "Phi-3.5-vision-instruct", false, true), GPT_4O_MINI("gpt-4o-mini", false, true), LLAMA_3_2_11B_VISION_INSTRUCT(
                  "Llama-3.2-11B-Vision-Instruct", false, true), MISTRAL_SMALL_2503("mistral-small-2503", false, true);

  private final ModelCapabilitySet capabilities;

  GithubModelName(String value, boolean textGenerationSupport, boolean visionSupport) {
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
