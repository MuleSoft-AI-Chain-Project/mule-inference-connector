package com.mulesoft.connectors.inference.internal.llmmodels.vertexai;

public enum VertexAIExpressModelName {
    GEMINI_20_FLASH_001("gemini-2.0-flash-001", true, true),
    GEMINI_20_FLASH_LITE_001("gemini-2.0-flash-lite-001", true, true),
    GEMINI_20_PRO_EXP_02_05("gemini-2.0-pro-exp-02-05", true, true),  //Experimental
    GEMINI_15_FLASH_002("gemini-1.5-flash-002", true, true),  //expires 9/24/25
    GEMINI_15_PRO_002("gemini-1.5-pro-002", true, true),      //expires 9/24/25
    GEMINI_10_PRO_002("gemini-1.0-pro-002", true, true);      //expires 4/9/25

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