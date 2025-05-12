/**
 * (c) 2003-2024 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.internal.models;


import com.mulesoft.connectors.internal.exception.error.ConfigValidationException;
import com.mulesoft.connectors.internal.models.mistral.MistralAIModelName;
import com.mulesoft.connectors.internal.models.openai.OpenAIModelName;
import com.mulesoft.connectors.internal.models.openrouter.OpenRouterModelName;

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
      VERTEX_AI_EXPRESS("VERTEX_AI_EXPRESS", getVertexAIExpressModelNameStream()),
      VERTEX_AI("VERTEX_AI", getVertexAIModelNameStream()),
      AZURE_AI_FOUNDRY("AZURE_AI_FOUNDRY", getAzureAIFoundryModelNameStream()),
      GPT4ALL("GPT4ALL", getGPT4ALLModelNameStream()),
      LMSTUDIO("LMSTUDIO", getLMSTUDIOModelNameStream()),
      DOCKER_MODELS("DOCKER_MODELS", getDOCKER_MODELSNameStream()),
      DEEPSEEK("DEEPSEEK", getDEEPSEEKModelNameStream()),
      ZHIPU_AI("ZHIPU_AI", getCHATGLMModelNameStream()),
      OPENAI_COMPATIBLE_ENDPOINT("OPENAI_COMPATIBLE_ENDPOINT", getOpenAIModelNameStream()),
      IBM_WATSON("IBM_WATSON", getIBMWatsonModelNameStream()),
      DATABRICKS("DATABRICKS", getDATABRICKSModelNameStream()),
      LLM_API("LLM_API", getLLAMAAPIModelNameStream());


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

  private static Stream<String> getIBMWatsonModelNameStream() {
    return Arrays.stream(IBMWatsonModelName.values()).map(String::valueOf);
  }

  private static Stream<String> getPortkeyModelNameStream() {
    return Arrays.stream(PortkeyModelName.values()).map(String::valueOf);
  }

  private static Stream<String> getDATABRICKSModelNameStream() {
    return Arrays.stream(DatabricksModelName.values()).map(String::valueOf);
  }

  private static Stream<String> getLLAMAAPIModelNameStream() {
    return Arrays.stream(LlamaAPIModelName.values()).map(String::valueOf);
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
    return Arrays.stream(MistralAIModelName.values()).map(String::valueOf);
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

  private static Stream<String> getGPT4ALLModelNameStream() {
    return Arrays.stream(GPT4ALLModelName.values()).map(String::valueOf);
  }

  private static Stream<String> getLMSTUDIOModelNameStream() {
    return Arrays.stream(LMSTUDIOModelName.values()).map(String::valueOf);
  }
  private static Stream<String> getDOCKER_MODELSNameStream() {
    return Arrays.stream(DockerModelName.values()).map(String::valueOf);
  }
  private static Stream<String> getDEEPSEEKModelNameStream() {
    return Arrays.stream(DEEPSEEKModelName.values()).map(String::valueOf);
  }

  private static Stream<String> getCHATGLMModelNameStream() {
    return Arrays.stream(CHATGLMModelName.values()).map(String::valueOf);
  }

  public static ModelType fromValue(String value) {
    return Arrays.stream(ModelType.values())
        .filter(langchainLLMType -> langchainLLMType.value.equals(value))
        .findFirst()
        .orElseThrow(() -> new ConfigValidationException("Unsupported LLM Type: " + value));
  }
}
