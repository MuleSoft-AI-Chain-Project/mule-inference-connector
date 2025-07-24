package com.mulesoft.connectors.inference.internal.llmmodels.anthropic;

import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilities;
import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilitySet;

public enum AnthropicModelName implements ModelCapabilities {

  CLAUDE_3_7_SONNET_LATEST("claude-3-7-sonnet-latest", true, false), CLAUDE_3_5_HAIKU_LATEST("claude-3-5-haiku-latest", true,
      false), CLAUDE_3_5_SONNET_LATEST("claude-3-5-sonnet-latest", true, false), CLAUDE_3_OPUS_LATEST("claude-3-opus-latest",
          true, false), CLAUDE_3_7_SONNET_20250219("claude-3-7-sonnet-20250219", false, true), CLAUDE_3_5_HAIKU_20241022(
              "claude-3-5-haiku-20241022", false, true), CLAUDE_3_5_SONNET_20241022("claude-3-5-sonnet-20241022", false,
                  true), CLAUDE_3_OPUS_20240229("claude-3-opus-20240229", false, true);

  private final ModelCapabilitySet capabilities;

  AnthropicModelName(String value, boolean textGenerationSupport, boolean visionSupport) {
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
