/**
 * (c) 2003-2025 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.inference.internal.constants;

public class InferenceConstants {

  private InferenceConstants() {}

  public static final String PAYLOAD_LOGGER_MSG = "Payload sent to the LLM {}";

  public static final String HEADER_CONTENT_TYPE = "Content-Type";


  public static final String PORTKEY_URL = "https://api.portkey.ai/v1";
  public static final String HUGGINGFACE_URL = "https://router.huggingface.co/hf-inference";
  public static final String GROQ_URL = "https://api.groq.com/openai/v1";
  public static final String OPENROUTER_URL = "https://openrouter.ai/api/v1";
  public static final String GITHUB_MODELS_URL = "https://models.inference.ai.azure.com";
  public static final String CEREBRAS_URL = "https://api.cerebras.ai/v1";
  public static final String NVIDIA_URL = "https://integrate.api.nvidia.com/v1";
  public static final String FIREWORKS_URL = "https://api.fireworks.ai/inference/v1";
  public static final String TOGETHER_URL = "https://api.together.xyz/v1";
  public static final String DEEPINFRA_URL = "https://api.deepinfra.com/v1/openai";
  public static final String PERPLEXITY_URL = "https://api.perplexity.ai";
  public static final String X_AI_URL = "https://api.x.ai/v1";
  public static final String OPEN_AI_URL = "https://api.openai.com/v1";
  public static final String MISTRAL_AI_URL = "https://api.mistral.ai/v1";
  public static final String ANTHROPIC_URL = "https://api.anthropic.com/v1";
  public static final String AI21LABS_URL = "https://api.ai21.com/studio/v1";
  public static final String COHERE_URL = "https://api.cohere.com/v2";
  public static final String AZURE_OPENAI_URL = "https://{resource-name}.openai.azure.com/openai/deployments/{deployment-id}";
  public static final String VERTEX_AI_EXPRESS_URL = "https://aiplatform.googleapis.com/v1/publishers/google/models/{MODEL_ID}:";
  public static final String AZURE_AI_FOUNDRY_URL = "https://{resource-name}.services.ai.azure.com/models";
  public static final String GPT4ALL_URL = "http://localhost:4891/v1";
  public static final String LMSTUDIO_URL = "http://localhost:1234/v1";
  public static final String DOCKER_MODEL_URL = "http://localhost:12434";
  public static final String DEEPSEEK_URL = "https://api.deepseek.com";
  public static final String ZHIPU_AI_URL = "https://open.bigmodel.cn/api/paas/v4";
  public static final String VERTEX_AI_GEMINI_URL =
      "https://{LOCATION_ID}-aiplatform.googleapis.com/v1/projects/{PROJECT_ID}/locations/{LOCATION_ID}/publishers/google/models/{MODEL_ID}:";
  public static final String VERTEX_AI_ANTHROPIC_URL =
      "https://{LOCATION_ID}-aiplatform.googleapis.com/v1/projects/{PROJECT_ID}/locations/{LOCATION_ID}/publishers/anthropic/models/{MODEL_ID}:";
  public static final String VERTEX_AI_META_URL =
      "https://{LOCATION_ID}-aiplatform.googleapis.com/v1beta1/projects/{PROJECT_ID}/locations/{LOCATION_ID}/endpoints/openapi/chat/completions";
  public static final String OPENAI_COMPATIBLE_ENDPOINT = "https://server.endpoint.com";
  public static final String IBM_WATSON_URL = "https://us-south.ml.cloud.ibm.com/ml/v1/text";
  public static final String IBM_WATSON_TOKEN_URL = "https://iam.cloud.ibm.com/identity/token";
  public static final String STABILITY_AI_URL = "https://api.stability.ai";
  public static final String LLM_API_URL = "https://api.llmapi.com";

  // Resources
  public static final String CHAT_COMPLETIONS = "/chat/completions";
  public static final String CHAT_COMPLETIONS_AZURE = "/chat/completions?api-version=2024-10-21";
  public static final String CHAT_COMPLETIONS_AZURE_AI_FOUNDRY = "/chat/completions?api-version={api-version}";
  public static final String CHAT_COMPLETIONS_IBM_WATSON = "/chat?version={api-version}";
  public static final String CHAT_COMPLETIONS_OLLAMA = "/chat";
  public static final String GENERATION_OLLAMA = "/generate";
  public static final String GENERATE_CONTENT_VERTEX_AI_GEMINI = "generateContent";
  public static final String GENERATE_CONTENT_VERTEX_AI_ANTHROPIC = "rawPredict";
  public static final String OPENAI_GENERATE_IMAGES = "/images/generations";
  public static final String VERTEX_AI_ANTHROPIC_VERSION = "anthropic_version";
  public static final String VERTEX_AI_ANTHROPIC_VERSION_VALUE = "vertex-2023-10-16";
  public static final String CHAT_COMPLETIONS_DATABRICKS = "/serving-endpoints/{model_name}/invocations";
  public static final String STABILITY_AI_GENERATE_IMAGES = "/v2beta/stable-image/generate/sd3";

  public static final String MODERATIONS_PATH = "/moderations";


}

