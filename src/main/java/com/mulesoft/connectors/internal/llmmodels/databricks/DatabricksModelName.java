package com.mulesoft.connectors.internal.llmmodels.databricks;

public enum DatabricksModelName {
    // only using chat model types.
    DATABRICKS_LLAMA_4_MAVERICK("databricks-llama-4-maverick", true),
    DATABRICKS_CLAUDE_3_7_SONNET("databricks-claude-3-7-sonnet", true),
    DATABRICKS_META_LLAMA_3_1_8B_INSTRUCT("databricks-meta-llama-3-1-8b-instruct", true),
    DATABRICKS_META_LLAMA_3_3_70B_INSTRUCT("databricks-meta-llama-3-3-70b-instruct", true),
    DATABRICKS_META_LLAMA_3_1_405B_INSTRUCT("databricks-meta-llama-3-1-405b-instruct", true),
    DATABRICKS_DBRX_INSTRUCT("databricks-dbrx-instruct", true),
    DATABRICKS_MIXTRAL_8X7B_INSTRUCT("databricks-mixtral-8x7b-instruct", true);

    private final String value;
    private final boolean textGenerationSupport;

    DatabricksModelName(String value, boolean textGenerationSupport) {
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