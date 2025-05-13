/**
 * (c) 2003-2024 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.internal.models.vision;


import com.mulesoft.connectors.internal.exception.error.ConfigValidationException;

import java.util.Arrays;
import java.util.stream.Stream;

@Deprecated
public enum ModelType {
      /*
      XINFERENCE("XINFERENCE", getXinferenceModelNameStream()),
      NVIDIA("NVIDIA", getNVidiaModelNameStream()),
      FIREWORKS("FIREWORKS", getFireworksModelNameStream()),
      TOGETHER("TOGETHER", getTOGETHERModelNameStream()),
      DEEPINFRA("DEEPINFRA", getDEEPINFRAModelNameStream()),
      PERPLEXITY("PERPLEXITY", getPERPLEXITYModelNameStream()),
      AI21LABS("AI21LABS", getAI21LABSModelNameStream()),
      COHERE("COHERE", getCohereModelNameStream()),
      OLLAMA("OLLAMA", getOllamaModelNameStream()),
      AZURE_OPENAI("AZURE_OPENAI", getAzureOpenAIModelNameStream()),*/
      AZURE_AI_FOUNDRY("AZURE_AI_FOUNDRY", getAzureAIFoundryModelNameStream()),
      MISTRAL("MISTRAL", getMistralModelNameStream()),
      OPENAI("OPENAI", getOpenAIModelNameStream()),
      ANTHROPIC("ANTHROPIC", getAnthropicModelNameStream()),
      XAI("XAI", getXAIModelNameStream()),
      GROQ("GROQ", getGroqModelNameStream()),
      OPENROUTER("OPENROUTER", getOpenRouterModelNameStream()),
      PORTKEY("PORTKEY", getPortkeyModelNameStream()),
      HUGGING_FACE("HUGGING_FACE", getHuggingFaceModelNameStream()),
      GITHUB("GITHUB", getGithubModelNameStream()),
      OLLAMA("OLLAMA", getOllamaModelNameStream()),
      VERTEX_AI_EXPRESS("VERTEX_AI_EXPRESS", getVertexAIExpressModelNameStream()),
      VERTEX_AI("VERTEX_AI", getVertexAIModelNameStream()),

  ;

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
  
  private static Stream<String> getVertexAIModelNameStream() {
	    return Arrays.stream(VertexAIModelName.values()).map(String::valueOf);
  }

  private static Stream<String> getAzureAIFoundryModelNameStream() {
    return Arrays.stream(AzureAIFoundryModelName.values()).map(String::valueOf);
  }



  public static ModelType fromValue(String value) {
    return Arrays.stream(ModelType.values())
        .filter(langchainLLMType -> langchainLLMType.value.equals(value))
        .findFirst()
        .orElseThrow(() -> new ConfigValidationException("Unsupported LLM Type: " + value));
  }

  enum XAIModelName {

    grok_vision_beta("grok-vision-beta"),
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
    chatgpt_4o_latest("chatgpt-4o-latest"),
    gpt_4o("gpt-4o"),
    gpt_4o_mini("gpt-4o-mini"),
    gpt_4_turbo("gpt-4-turbo");

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

    mistral_small_latest("mistral-small-latest"),
    pixtral_12b_latest("pixtral-12b-latest"),
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

    claude_3_7_sonnet_20250219("claude-3-7-sonnet-20250219"),
    claude_3_5_haiku_20241022("claude-3-5-haiku-20241022"),
    claude_3_5_sonnet_20241022("claude-3-5-sonnet-20241022"),
    claude_3_opus_20240229("claude-3-opus-20240229");

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
    LLAMA_3_2_90b_VISION_PREVIEW("llama-3.2-90b-vision-preview"),
    LLAMA_3_2_11b_VISION_PREVIEW("llama-3.2-11b-vision-preview"),
    ;
    
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
    Qwen_Qwen2_5_VL_7B_Instruct("Qwen/Qwen2.5-VL-7B-Instruct"),
    Qwen_Qwen2_VL_7B_Instruct("Qwen/Qwen2-VL-7B-Instruct"),
    google_gemma_3_27b_it("google/gemma-3-27b-it"),
    meta_llama_Llama_3_2_11B_Vision_Instruct("meta-llama/Llama-3.2-11B-Vision-Instruct"),
    ;

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
    gpt_4_vision_preview("gpt-4-vision-preview"),
      ;
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
    Phi_3_5_vision_instruct("Phi-3.5-vision-instruct"),
    gpt_4o_mini("gpt-4o-mini"),
    Llama_3_2_11B_Vision_Instruct("Llama-3.2-11B-Vision-Instruct"),
    mistral_small_2503("mistral-small-2503"),
    ;
    
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
    mistralai_mistral_small_3_1_24b_instruct_free("mistralai/mistral-small-3.1-24b-instruct:free"),
    google_gemma_3_1b_it_free("google/gemma-3-1b-it:free"),
    META_LLAMA_3_2_90B_VISION_INSTRUCT_FREE("meta-llama/llama-3.2-90b-vision-instruct:free"),
    META_LLAMA_3_2_11B_VISION_INSTRUCT_FREE("meta-llama/llama-3.2-11b-vision-instruct:free"),
    META_LLAMA_3_2_11B_VISION_INSTRUCT("meta-llama/llama-3.2-11b-vision-instruct"),
  ;

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
    llava_phi3("llava-phi3");

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
  
	enum VertexAIModelName {
	    GEMINI_20_FLASH_001("gemini-2.0-flash-001"), 
	    GEMINI_20_FLASH_LITE_001("gemini-2.0-flash-lite-001"),
	    GEMINI_15_FLASH_002("gemini-1.5-flash-002"),    //expires 9/24/25
	    GEMINI_15_PRO_002("gemini-1.5-pro-002"),      //expires 9/24/25
	    CLAUDE_37_SONNET_20250219("claude-3-7-sonnet@20250219"),
	    CLAUDE_35_HAIKU_20241022("claude-3-5-haiku@20241022"),
	    CLAUDE_35_SONNET_20241022("claude-3-5-haiku@20241022"),
	    CLAUDE_3_OPUS_20240229("claude-3-opus@20240229"),
	    META_LLAMA_4_MAVERICK_INSTRUCT("meta/llama-4-maverick-17b-128e-instruct-maas"),
	    META_LLAMA_4_SCOUT_INSTRUCT("meta/llama-4-scout-17b-16e-instruct-maas"),
	    META_LLAMA_33_70B_INSTRUCT("meta/llama-3.3-70b-instruct-maas"),
	    META_LLAMA_31_70B_INSTRUCT("meta/llama-3.1-70b-instruct-maas"),
	    META_LLAMA_31_405B_INSTRUCT("meta/llama-3.1-405b-instruct-maas"),
	    META_LLAMA_32_90B_INSTRUCT("meta/llama-3.2-90b-vision-instruct-maas");  

	    private final String value;

	    VertexAIModelName(String value) {
	      this.value = value;
	    }

	    @Override
	    public String toString() {
	      return this.value;
	    }
	}

  enum AzureAIFoundryModelName {

    Phi_3_5_vision_instruct("Phi-3.5-vision-instruct"),
    Phi_4_multimodal_instruct("Phi-4-multimodal-instruct");
    
    private final String value;

    AzureAIFoundryModelName(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return this.value;
    }
  }


}
