package com.mulesoft.connectors.internal.models;

enum VertexAIExpressModelName {
    GEMINI_20_FLASH_001("gemini-2.0-flash-001"),
    GEMINI_20_FLASH_LITE_001("gemini-2.0-flash-lite-001"),
    GEMINI_20_PRO_EXP_02_05("gemini-2.0-pro-exp-02-05"),  //Experimental
    GEMINI_15_FLASH_002("gemini-1.5-flash-002"),  //expires 9/24/25
    GEMINI_15_PRO_002("gemini-1.5-pro-002"),      //expires 9/24/25
    GEMINI_10_PRO_002("gemini-1.0-pro-002");      //expires 4/9/25

    private final String value;

    VertexAIExpressModelName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
