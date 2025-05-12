package com.mulesoft.connectors.internal.models.openrouter;

public enum OpenRouterModelName {
    ANTHROPIC_CLAUDE_3_5_SONNET("anthropic/claude-3.5-sonnet"),
    ANTHROPIC_CLAUDE_3_5_SONNET_SELF_MODERATED("anthropic/claude-3.5-sonnet:beta"),
    MINISTRAL_8B("mistralai/ministral-8b"),
    MINISTRAL_3B("mistralai/ministral-3b"),
    NVIDIA_LLAMA_3_1_NEMOTRON_70B_INSTRUCT("nvidia/llama-3.1-nemotron-70b-instruct"),
    GOOGLE_GEMINI_FLASH_8B_1_5("google/gemini-flash-1.5-8b"),
    LIQUID_LFM_40B_MOE_FREE("liquid/lfm-40b:free"),
    LIQUID_LFM_40B_MOE("liquid/lfm-40b"),
    META_LLAMA_3_2_3B_INSTRUCT_FREE("meta-llama/llama-3.2-3b-instruct:free"),
    META_LLAMA_3_2_3B_INSTRUCT("meta-llama/llama-3.2-3b-instruct"),
    META_LLAMA_3_2_1B_INSTRUCT_FREE("meta-llama/llama-3.2-1b-instruct:free"),
    META_LLAMA_3_2_1B_INSTRUCT("meta-llama/llama-3.2-1b-instruct"),
    META_LLAMA_3_2_90B_VISION_INSTRUCT_FREE("meta-llama/llama-3.2-90b-vision-instruct:free"),
    META_LLAMA_3_2_90B_VISION_INSTRUCT("meta-llama/llama-3.2-90b-vision-instruct"),
    META_LLAMA_3_2_11B_VISION_INSTRUCT_FREE("meta-llama/llama-3.2-11b-vision-instruct:free"),
    META_LLAMA_3_2_11B_VISION_INSTRUCT("meta-llama/llama-3.2-11b-vision-instruct"),
    QWEN_2_5_72B_INSTRUCT("qwen/qwen-2.5-72b-instruct"),
    LUMIMAID_V0_2_8B("neversleep/llama-3.1-lumimaid-8b"),
    MISTRAL_PIXTRAL_12B("mistralai/pixtral-12b"),
    COHERE_COMMAND_R_PLUS_08_2024("cohere/command-r-plus-08-2024"),
    COHERE_COMMAND_R_08_2024("cohere/command-r-08-2024"),
    GOOGLE_GEMINI_FLASH_8B_1_5_EXPERIMENTAL("google/gemini-flash-1.5-8b-exp"),
    LLAMA_3_1_EURYALE_70B_V2_2("sao10k/l3.1-euryale-70b"),
    GOOGLE_GEMINI_FLASH_1_5_EXPERIMENTAL("google/gemini-flash-1.5-exp"),
    AI21_JAMBA_1_5_LARGE("ai21/jamba-1-5-large"),
    AI21_JAMBA_1_5_MINI("ai21/jamba-1-5-mini"),
    PHI_3_5_MINI_128K_INSTRUCT("microsoft/phi-3.5-mini-128k-instruct"),
    PERPLEXITY_LLAMA_3_1_SONAR_8B_ONLINE("perplexity/llama-3.1-sonar-small-128k-online"),
    MISTRAL_NEMO("mistralai/mistral-nemo"),
    OPENAI_GPT_4O_MINI_2024_07_18("openai/gpt-4o-mini-2024-07-18"),
    OPENAI_GPT_4O_MINI("openai/gpt-4o-mini"),
    GOOGLE_GEMMA_2_9B_FREE("google/gemma-2-9b-it:free");

    private final String value;

    OpenRouterModelName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
