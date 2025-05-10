package com.mulesoft.connectors.internal.models;

enum IBMWatsonModelName {
    meta_llama_llama_3_2_3b_instruct("meta-llama/llama-3-2-3b-instruct"),
    ibm_granite_20b_code_instruct("ibm/granite-3-2b-instruct"),
    meta_llama_llama_3_2_11b_vison_instruct("meta-llama/llama-3-2-11b-vision-instruct"),
    meta_llama_llama_3_1_70_instruct("meta-llama/llama-3-1-70b-instruct"),
    ibm_granite_vision_2_2_2b("ibm/granite-vision-3-2-2b"),
    ibm_granite_3_8b_instruct("ibm/granite-3-8b-instruct"),
    ibm_granite_3_2b_instruct("ibm/granite-3-2b-instruct"),

    ;

    private final String value;

    IBMWatsonModelName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
