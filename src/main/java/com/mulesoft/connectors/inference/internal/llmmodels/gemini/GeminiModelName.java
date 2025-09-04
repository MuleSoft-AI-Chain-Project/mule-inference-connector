package com.mulesoft.connectors.inference.internal.llmmodels.gemini;

import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilities;
import com.mulesoft.connectors.inference.internal.llmmodels.ModelCapabilitySet;

public enum GeminiModelName implements ModelCapabilities {

  GEMINI_25_FLASH("gemini-2.5-flash", true, true), GEMINI_25_PRO("gemini-2.5-pro", true,
      true), GEMINI_25_FLASH_LITE_PREVIEW_EXP_06_17("gemini-2.5-flash-lite-preview-06-17", true,
          true), GEMINI_20_FLASH_001("gemini-2.0-flash-001", true, true), GEMINI_20_FLASH_LITE_001("gemini-2.0-flash-lite-001",
              true, true), GEMINI_15_FLASH_002("gemini-1.5-flash-002", true, true), // expires 9/24/25
  GEMINI_15_PRO_002("gemini-1.5-pro-002", true, true); // expires 9/24/25

  private final ModelCapabilitySet capabilities;

  GeminiModelName(String value, boolean textGenerationSupport, boolean visionSupport) {
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

