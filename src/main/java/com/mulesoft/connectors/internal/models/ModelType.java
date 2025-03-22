/**
 * (c) 2003-2024 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.internal.models;


import com.mulesoft.connectors.internal.exception.error.ConfigValidationException;

import java.util.Arrays;
import java.util.stream.Stream;

public enum ModelType {
      HUGGING_FACE("HUGGING_FACE", getHuggingFaceModelNameStream()),
      GROQ("GROQ", getGroqModelNameStream()),
      PORTKEY("PORTKEY", getPortkeyModelNameStream()),
      OPENROUTER("OPENROUTER", getOpenRouterModelNameStream()),
      GITHUB("GITHUB", getGithubModelNameStream()),
      OLLAMA("OLLAMA", getOllamaModelNameStream()),
      XINFERENCE("XINFERENCE", getXinferenceModelNameStream()),
      CEREBRAS("CEREBRAS", getCerebrasModelNameStream()),
      NVIDIA("NVIDIA", getNVidiaModelNameStream()),
      FIREWORKS("FIREWORKS", getFireworksModelNameStream()),
      TOGETHER("TOGETHER", getTOGETHERModelNameStream()),
      DEEPINFRA("DEEPINFRA", getDEEPINFRAModelNameStream()),
      PERPLEXITY("PERPLEXITY", getPERPLEXITYModelNameStream()),
      OPENAI("OPENAI", getOpenAIModelNameStream()),
      MISTRAL("MISTRAL", getMistralModelNameStream()),
      ANTHROPIC("ANTHROPIC", getAnthropicModelNameStream()),
      AI21LABS("AI21LABS", getAI21LABSModelNameStream()),
      COHERE("COHERE", getCohereModelNameStream()),
      XAI("XAI", getXAIModelNameStream()),
      AZURE_OPENAI("AZURE_OPENAI", getAzureOpenAIModelNameStream()),
      VERTEX_AI_EXPRESS("VERTEX_AI_EXPRESS", getVertexAIExpressModelNameStream());


  private final String value;
  private final Stream<String> modelNameStream;

  ModelType(String value, Stream<String> modelNameStream) {
    this.value = value;
    this.modelNameStream = modelNameStream;
  }

  public String getValue() {
    return value;
  }

  public Stream<String> getModelNameStream() {
    return modelNameStream;
  }

  private static Stream<String> getHuggingFaceModelNameStream() {
    return Arrays.stream(HuggingFaceModelName.values()).map(String::valueOf);
  }

  private static Stream<String> getGroqModelNameStream() {
    return Arrays.stream(GroqModelName.values()).map(String::valueOf);
  }


  private static Stream<String> getPortkeyModelNameStream() {
    return Arrays.stream(PortkeyModelName.values()).map(String::valueOf);
  }

  private static Stream<String> getOpenRouterModelNameStream() {
    return Arrays.stream(OpenRouterModelName.values()).map(String::valueOf);
  }


  private static Stream<String> getGithubModelNameStream() {
    return Arrays.stream(GithubModelName.values()).map(String::valueOf);
  }

  private static Stream<String> getOllamaModelNameStream() {
    return Arrays.stream(OllamaModelName.values()).map(String::valueOf);
  }

  private static Stream<String> getXinferenceModelNameStream() {
    return Arrays.stream(XinferenceModelName.values()).map(String::valueOf);
  }


  private static Stream<String> getCerebrasModelNameStream() {
    return Arrays.stream(CerebrasModelName.values()).map(String::valueOf);
  }

  private static Stream<String> getNVidiaModelNameStream() {
    return Arrays.stream(NVidiaModelName.values()).map(String::valueOf);
  }


  private static Stream<String> getFireworksModelNameStream() {
    return Arrays.stream(FireworksModelName.values()).map(String::valueOf);
  }


  private static Stream<String> getTOGETHERModelNameStream() {
    return Arrays.stream(TogetherModelName.values()).map(String::valueOf);
  }


  private static Stream<String> getDEEPINFRAModelNameStream() {
    return Arrays.stream(DeepinfraModelName.values()).map(String::valueOf);
  }

  private static Stream<String> getPERPLEXITYModelNameStream() {
    return Arrays.stream(PerplexityModelName.values()).map(String::valueOf);
  }

  private static Stream<String> getOpenAIModelNameStream() {
    return Arrays.stream(OpenAIModelName.values()).map(String::valueOf);
  }

  private static Stream<String> getMistralModelNameStream() {
    return Arrays.stream(MistralModelName.values()).map(String::valueOf);
  }

  private static Stream<String> getAnthropicModelNameStream() {
    return Arrays.stream(AnthropicModelName.values()).map(String::valueOf);
  }

  private static Stream<String> getAI21LABSModelNameStream() {
    return Arrays.stream(AI21LABSModelName.values()).map(String::valueOf);
  }

  private static Stream<String> getCohereModelNameStream() {
    return Arrays.stream(CohereModelName.values()).map(String::valueOf);
  }

  private static Stream<String> getXAIModelNameStream() {
    return Arrays.stream(XAIModelName.values()).map(String::valueOf);
  }

  private static Stream<String> getAzureOpenAIModelNameStream() {
    return Arrays.stream(AzureOpenAIModelName.values()).map(String::valueOf);
  }
  
  private static Stream<String> getVertexAIExpressModelNameStream() {
	    return Arrays.stream(VertexAIExpressModelName.values()).map(String::valueOf);
  }
  

  public static ModelType fromValue(String value) {
    return Arrays.stream(ModelType.values())
        .filter(langchainLLMType -> langchainLLMType.value.equals(value))
        .findFirst()
        .orElseThrow(() -> new ConfigValidationException("Unsupported LLM Type: " + value));
  }

  enum XAIModelName {

    grok_2_1212("grok-2-1212"),
    grok_2_vision_1212("grok-2-vision-1212");

    private final String value;

    XAIModelName(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return this.value;
    }
  }

  enum AI21LABSModelName {

    jamba_large("jamba-large"),
    jamba_mini("jamba-mini");

    private final String value;

    AI21LABSModelName(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return this.value;
    }
  }


  enum CohereModelName {

    command_r7b_12_2024("command-r7b-12-2024"),
    command_r_plus_08_2024("command-r-plus-08-2024"),
    command_r_plus("command-r-plus"),
    command_r("command-r"),
    command("command"),
    command_r_plus_04_2024("command-r-plus-04-2024");

    private final String value;

    CohereModelName(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return this.value;
    }
  }

  enum OpenAIModelName {

    gpt_4_5_preview("gpt-4.5-preview"),
    o1_mini("o1-mini"),
    chatgpt_4o_latest("chatgpt-4o-latest"),
    gpt_4o("gpt-4o"),
    gpt_4o_mini("gpt-4o-mini");

    private final String value;

    OpenAIModelName(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return this.value;
    }
  }

  enum MistralModelName {

    mistral_large_latest("mistral-large-latest"),
    mistral_small_latest("mistral-small-latest"),
    open_mistral_nemo("open-mistral-nemo"),
    pixtral_large_latest("pixtral-large-latest");

    private final String value;

    MistralModelName(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return this.value;
    }
  }

  enum AnthropicModelName {

    claude_3_7_sonnet_latest("claude-3-7-sonnet-latest"),
    claude_3_5_haiku_latest("claude-3-5-haiku-latest"),
    claude_3_5_sonnet_latest("claude-3-5-sonnet-latest"),
    claude_3_opus_latest("claude-3-opus-latest");

    private final String value;

    AnthropicModelName(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return this.value;
    }
  }


  enum PerplexityModelName {

    sonar("sonar"),
    sonar_pro("sonar-pro"),
    sonar_reasoning("sonar-reasoning"),
    sonar_reasoning_pro("sonar-reasoning-pro"),
    sonar_deep_research("sonar-deep-research");

    private final String value;

    PerplexityModelName(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return this.value;
    }
  }

  enum DeepinfraModelName {
    
    LLAMA_3_8B_INSTRUCT("meta-llama/Meta-Llama-3-8B-Instruct");
    
    private final String value;
  
    DeepinfraModelName(String value) {
      this.value = value;
    }
  
    @Override
    public String toString() {
      return this.value;
    }
  }

  enum TogetherModelName {
    
    LLAMA_3_1_8B_INSTRUCT_TURBO("meta-llama/Meta-Llama-3.1-8B-Instruct-Turbo");
    
    private final String value;
  
    TogetherModelName(String value) {
      this.value = value;
    }
  
    @Override
    public String toString() {
      return this.value;
    }
  }

  enum FireworksModelName {
    LLAMA_V3P1_405B_INSTRUCT("accounts/fireworks/models/llama-v3p1-405b-instruct");
    
    private final String value;
  
    FireworksModelName(String value) {
      this.value = value;
    }
  
    @Override
    public String toString() {
      return this.value;
    }
  }

  enum GroqModelName {
    MIXTRAL_8x7b_32768("mixtral-8x7b-32768"),
    LLAMA_3_2_3b_PREVIEW("llama-3.2-3b-preview"),
    LLAMA_3_70b_8192("llama3-70b-8192"),
    LLAMA_3_2_90b_VISION_PREVIEW("llama-3.2-90b-vision-preview"),
    LLAMA_3_2_11b_TEXT_PREVIEW("llama-3.2-11b-text-preview"),
    LLAMA_3_2_1b_PREVIEW("llama-3.2-1b-preview"),
    GEMMA2_9b_IT("gemma2-9b-it"),
    LLAMA3_GROQ_8b_8192_TOOL_USE_PREVIEW("llama3-groq-8b-8192-tool-use-preview"),
    LLAVA_V1_5_7b_4096_PREVIEW("llava-v1.5-7b-4096-preview"),
    LLAMA_3_2_11b_VISION_PREVIEW("llama-3.2-11b-vision-preview"),
    LLAMA_3_2_90b_TEXT_PREVIEW("llama-3.2-90b-text-preview"),
    LLAMA_3_1_8b_INSTANT("llama-3.1-8b-instant"),
    LLAMA3_8b_8192("llama3-8b-8192"),
    LLAMA3_GROQ_70b_8192_TOOL_USE_PREVIEW("llama3-groq-70b-8192-tool-use-preview"),
    LLAMA_3_1_70b_VERSATILE("llama-3.1-70b-versatile"),
    GEMMA_7b_IT("gemma-7b-it"),
    LLAMA_GUARD_3_8b("llama-guard-3-8b");
    
    private final String value;

    GroqModelName(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return this.value;
    }
  }

  enum HuggingFaceModelName {
    TII_UAE_FALCON_7B_INSTRUCT("tiiuae/falcon-7b-instruct"), 
    PHI3("microsoft/Phi-3.5-mini-instruct"), 
    MISTRAL_7B_INSTRUCT_V03("mistralai/Mistral-7B-Instruct-v0.3"), 
    TINY_LLAMA("TinyLlama/TinyLlama-1.1B-Chat-v1.0");

    private final String value;

    HuggingFaceModelName(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return this.value;
    }
  }

  enum PortkeyModelName {
    GPT_4O("gpt-4o"),
    CHATGPT_4O_LATEST("chatgpt-4o-latest"),
    GPT_4O_MINI("gpt-4o-mini"),
    MISTRAL_LARGE_LATEST("mistral-large-latest"),
    MISTRAL_SMALL_LATEST("mistral-small-latest"),
    GPT_4_TURBO("gpt-4-turbo"),
    GPT_4_TURBO_PREVIEW("gpt-4-turbo-preview"),
    GPT_4("gpt-4"),
    GPT_3_5_TURBO("gpt-3.5-turbo");
    
    private final String value;

    PortkeyModelName(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return this.value;
    }
  }

  enum GithubModelName {
    GPT_4O("gpt-4o"),
    PHI_3_5_MOE_INSTRUCT("Phi-3.5-MoE-instruct"),
    MISTRAL_LARGE("Mistral-large"),
    MISTRAL_SMALL("Mistral-small"),
    GPT_4_TURBO("gpt-4-turbo"),
    AI21_JAMBA_1_5_LARGE("AI21-Jamba-1.5-Large"),
    COHERE_COMMAND_R("Cohere-command-r");
    
    private final String value;

    GithubModelName(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return this.value;
    }
  }

  enum OpenRouterModelName {
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

  enum OllamaModelName {
    MISTRAL("mistral"), PHI3("phi3"), ORCA_MINI("orca-mini"), LLAMA2("llama2"), CODE_LLAMA("codellama"), TINY_LLAMA("tinyllama");

    private final String value;

    OllamaModelName(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return this.value;
    }
  }

  enum XinferenceModelName {
    CHATGLM3_6("chatglm3-6b"),
    Qwen25_72B_Instruct("Qwen2.5-72B-Instruct"),
    Qwen25_32B_Instruct("Qwen2.5-32B-Instruct"),
    Qwen25_Coder_7B_Instruct("Qwen2.5-Coder-7B-Instruct"),
    glm_4_9b_chat("glm-4-9b-chat");

    private final String value;

    XinferenceModelName(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return this.value;
    }
  }

  enum CerebrasModelName {
    LLAMA3_1_8B("llama3.1-8b"), LLAMA3_1_70B("llama3.1-70b");

    private final String value;

    CerebrasModelName(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return this.value;
    }
  }


  enum NVidiaModelName {
    MISTRAL_7B_INSTRUCT_v0_3("mistralai/mistral-7b-instruct-v0.3"), AI_YI_LARGE("01-ai/yi-large");

    private final String value;

    NVidiaModelName(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return this.value;
    }
  }

  enum AzureOpenAIModelName {
    //The Model is not specified in the Azure OpenAI API but rather as part of the deployment configuration. In an ideal world we wouldn't need to specify a mdoel when using Azur OpenAI.
    AZURE_OPENAI("azure-openai"); 

    private final String value;

    AzureOpenAIModelName(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return this.value;
    }
  }
  
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
  
}
