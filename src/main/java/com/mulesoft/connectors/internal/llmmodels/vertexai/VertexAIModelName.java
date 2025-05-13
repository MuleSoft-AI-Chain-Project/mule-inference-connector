package com.mulesoft.connectors.internal.llmmodels.vertexai;

public enum VertexAIModelName {
    GEMINI_20_FLASH_001("gemini-2.0-flash-001", true,true),
    GEMINI_20_FLASH_LITE_001("gemini-2.0-flash-lite-001", true, true),
    GEMINI_15_FLASH_002("gemini-1.5-flash-002", true, true),    //expires 9/24/25
    GEMINI_15_PRO_002("gemini-1.5-pro-002", true, true),      //expires 9/24/25
    //	    GEMINI_25_PRO_PREVIEW_0325("gemini-2.5-pro-preview-03-25"),
//	    GEMINI_25_PRO_EXP_0325("gemini-2.5-pro-exp-03-25"),
//	    GEMINI_20_FLASH_EXP_IMAGE_GENERATION("gemini-2.0-flash-exp(image generation)"),
//	    GEMINI_25_FLASH_THINKING_EXP_0121("gemini-2.0-flash-thinking-exp-01-21"),
    CLAUDE_37_SONNET_20250219("claude-3-7-sonnet@20250219", true, true),
    CLAUDE_35_HAIKU_20241022("claude-3-5-haiku@20241022", true, true),
    CLAUDE_35_SONNET_20241022("claude-3-5-haiku@20241022", true, true),
    CLAUDE_3_OPUS_20240229("claude-3-opus@20240229", true, true),
    META_LLAMA_4_MAVERICK_INSTRUCT("meta/llama-4-maverick-17b-128e-instruct-maas", true, true),
    META_LLAMA_4_SCOUT_INSTRUCT("meta/llama-4-scout-17b-16e-instruct-maas", true, true),
    META_LLAMA_33_70B_INSTRUCT("meta/llama-3.3-70b-instruct-maas", true, true),
    META_LLAMA_31_70B_INSTRUCT("meta/llama-3.1-70b-instruct-maas", true, true),
    META_LLAMA_31_405B_INSTRUCT("meta/llama-3.1-405b-instruct-maas", true, true),
    META_LLAMA_32_90B_INSTRUCT("meta/llama-3.2-90b-vision-instruct-maas", true, true);

    private final String value;
    private final boolean textGenerationSupport;
    private final boolean visionSupport;

    VertexAIModelName(String value, boolean textGenerationSupport, boolean visionSupport) {
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