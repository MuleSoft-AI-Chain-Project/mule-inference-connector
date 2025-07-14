package com.mulesoft.connectors.inference.internal.llmmodels.vertexai;

public enum VertexAIExpressModelName {

  GEMINI_25_FLASH("gemini-2.5-flash", true, true), GEMINI_25_PRO("gemini-2.5-pro", true,
      true), GEMINI_25_FLASH_LITE_PREVIEW_EXP_06_17("gemini-2.5-flash-lite-preview-06-17", true,
          true), GEMINI_20_FLASH_001("gemini-2.0-flash-001", true, true), GEMINI_20_FLASH_LITE_001("gemini-2.0-flash-lite-001",
              true, true), GEMINI_15_FLASH_002("gemini-1.5-flash-002", true, true), // expires 9/24/25
  GEMINI_15_PRO_002("gemini-1.5-pro-002", true, true); // expires 9/24/25

  private final String value;
  private final boolean textGenerationSupport;
  private final boolean visionSupport;

  VertexAIExpressModelName(String value, boolean textGenerationSupport, boolean visionSupport) {
    this.value = value;
    this.textGenerationSupport = textGenerationSupport;
    this.visionSupport = visionSupport;
  }

  public boolean isTextGenerationSupport() {
    return textGenerationSupport;
  }

  public boolean isVisionSupport() {
    return visionSupport;
  }

  @Override
  public String toString() {
    return this.value;
  }
}
