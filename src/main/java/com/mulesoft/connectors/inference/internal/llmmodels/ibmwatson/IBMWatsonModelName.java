package com.mulesoft.connectors.inference.internal.llmmodels.ibmwatson;

public enum IBMWatsonModelName {
    META_LLAMA_LLAMA_3_2_3B_INSTRUCT("meta-llama/llama-3-2-3b-instruct", true),
    IBM_GRANITE_20B_CODE_INSTRUCT("ibm/granite-3-2b-instruct", true),
    META_LLAMA_LLAMA_3_2_11B_VISON_INSTRUCT("meta-llama/llama-3-2-11b-vision-instruct", true),
    META_LLAMA_LLAMA_3_1_70_INSTRUCT("meta-llama/llama-3-1-70b-instruct", true),
    IBM_GRANITE_VISION_2_2_2B("ibm/granite-vision-3-2-2b", true),
    IBM_GRANITE_3_8B_INSTRUCT("ibm/granite-3-8b-instruct", true),
    IBM_GRANITE_3_2B_INSTRUCT("ibm/granite-3-2b-instruct", true);

    private final String value;
    private final boolean textGenerationSupport;

    IBMWatsonModelName(String value, boolean textGenerationSupport) {
        this.value = value;
        this.textGenerationSupport = textGenerationSupport;
    }

    public boolean isTextGenerationSupport() {
        return textGenerationSupport;
    }

    @Override
    public String toString() {
        return this.value;
    }
} 