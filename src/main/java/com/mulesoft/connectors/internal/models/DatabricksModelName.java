package com.mulesoft.connectors.internal.models;

enum DatabricksModelName {
    // only using chat model types.
    databricks_llama_4_maverick("databricks-llama-4-maverick"),
    databricks_claude_3_7_sonnet("databricks-claude-3-7-sonnet"),
    databricks_meta_llama_3_1_8b_instruct("databricks-meta-llama-3-1-8b-instruct"),
    databricks_meta_llama_3_3_70b_instruct("databricks-meta-llama-3-3-70b-instruct"),
    databricks_meta_llama_3_1_405b_instruct("databricks-meta-llama-3-1-405b-instruct"),
    databricks_dbrx_instruct("databricks-dbrx-instruct"),
    databricks_mixtral_8x7b_instruct("databricks-mixtral-8x7b-instruct");


    private final String value;

    DatabricksModelName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
