package com.mulesoft.connectors.inference.internal.llmmodels.openrouter;

public enum OpenRouterModelName {

  ANTHROPIC_CLAUDE_3_5_SONNET("anthropic/claude-3.5-sonnet", true, false), ANTHROPIC_CLAUDE_3_5_SONNET_SELF_MODERATED(
      "anthropic/claude-3.5-sonnet:beta", true, false), MINISTRAL_8B("mistralai/ministral-8b", true, false), MINISTRAL_3B(
          "mistralai/ministral-3b", true, false), NVIDIA_LLAMA_3_1_NEMOTRON_70B_INSTRUCT("nvidia/llama-3.1-nemotron-70b-instruct",
              true, false), GOOGLE_GEMINI_FLASH_8B_1_5("google/gemini-flash-1.5-8b", true, false), LIQUID_LFM_40B_MOE_FREE(
                  "liquid/lfm-40b:free", true,
                  false), LIQUID_LFM_40B_MOE("liquid/lfm-40b", true, false), META_LLAMA_3_2_3B_INSTRUCT_FREE(
                      "meta-llama/llama-3.2-3b-instruct:free", true,
                      false), META_LLAMA_3_2_3B_INSTRUCT("meta-llama/llama-3.2-3b-instruct", true,
                          false), META_LLAMA_3_2_1B_INSTRUCT_FREE("meta-llama/llama-3.2-1b-instruct:free", true,
                              false), META_LLAMA_3_2_1B_INSTRUCT("meta-llama/llama-3.2-1b-instruct", true,
                                  false), META_LLAMA_3_2_90B_VISION_INSTRUCT_FREE("meta-llama/llama-3.2-90b-vision-instruct:free",
                                      true, true), META_LLAMA_3_2_90B_VISION_INSTRUCT("meta-llama/llama-3.2-90b-vision-instruct",
                                          true, false), META_LLAMA_3_2_11B_VISION_INSTRUCT_FREE(
                                              "meta-llama/llama-3.2-11b-vision-instruct:free", true,
                                              true), META_LLAMA_3_2_11B_VISION_INSTRUCT(
                                                  "meta-llama/llama-3.2-11b-vision-instruct", true,
                                                  true), QWEN_2_5_72B_INSTRUCT("qwen/qwen-2.5-72b-instruct", true,
                                                      false), LUMIMAID_V0_2_8B("neversleep/llama-3.1-lumimaid-8b", true,
                                                          false), MISTRAL_PIXTRAL_12B("mistralai/pixtral-12b", true,
                                                              false), COHERE_COMMAND_R_PLUS_08_2024(
                                                                  "cohere/command-r-plus-08-2024", true,
                                                                  false), COHERE_COMMAND_R_08_2024("cohere/command-r-08-2024",
                                                                      true, false), GOOGLE_GEMINI_FLASH_8B_1_5_EXPERIMENTAL(
                                                                          "google/gemini-flash-1.5-8b-exp", true,
                                                                          false), LLAMA_3_1_EURYALE_70B_V2_2(
                                                                              "sao10k/l3.1-euryale-70b", true,
                                                                              false), GOOGLE_GEMINI_FLASH_1_5_EXPERIMENTAL(
                                                                                  "google/gemini-flash-1.5-exp", true,
                                                                                  false), AI21_JAMBA_1_5_LARGE(
                                                                                      "ai21/jamba-1-5-large", true,
                                                                                      false), AI21_JAMBA_1_5_MINI(
                                                                                          "ai21/jamba-1-5-mini", true,
                                                                                          false), PHI_3_5_MINI_128K_INSTRUCT(
                                                                                              "microsoft/phi-3.5-mini-128k-instruct",
                                                                                              true,
                                                                                              false), PERPLEXITY_LLAMA_3_1_SONAR_8B_ONLINE(
                                                                                                  "perplexity/llama-3.1-sonar-small-128k-online",
                                                                                                  true, false), MISTRAL_NEMO(
                                                                                                      "mistralai/mistral-nemo",
                                                                                                      true,
                                                                                                      false), OPENAI_GPT_4O_MINI_2024_07_18(
                                                                                                          "openai/gpt-4o-mini-2024-07-18",
                                                                                                          true,
                                                                                                          false), OPENAI_GPT_4O_MINI(
                                                                                                              "openai/gpt-4o-mini",
                                                                                                              true,
                                                                                                              false), GOOGLE_GEMMA_2_9B_FREE(
                                                                                                                  "google/gemma-2-9b-it:free",
                                                                                                                  true,
                                                                                                                  false), MISTRALAI_MISTRAL_SMALL_3_1_24B_INSTRUCT_FREE(
                                                                                                                      "mistralai/mistral-small-3.1-24b-instruct:free",
                                                                                                                      false,
                                                                                                                      true), GOOGLE_GEMMA_3_1B_IT_FREE(
                                                                                                                          "google/gemma-3-1b-it:free",
                                                                                                                          false,
                                                                                                                          true);

  private final String value;
  private final boolean textGenerationSupport;
  private final boolean visionSupport;

  OpenRouterModelName(String value, boolean textGenerationSupport, boolean visionSupport) {
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
