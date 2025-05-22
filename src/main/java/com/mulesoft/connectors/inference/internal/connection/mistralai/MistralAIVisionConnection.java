package com.mulesoft.connectors.inference.internal.connection.mistralai;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import org.mule.runtime.http.api.client.HttpClient;

import java.util.Map;

public class MistralAIVisionConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String MISTRAL_AI_URL = "https://api.mistral.ai/v1";

  public MistralAIVisionConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                                   Number temperature, Number topP,
                                   Number maxTokens, int timeout) {
    super( httpClient, objectMapper, apiKey, modelName, maxTokens, temperature, topP, timeout, null,fetchApiURL(),"MistralAI");
  }

  private static String fetchApiURL() {
    return MISTRAL_AI_URL + URI_CHAT_COMPLETIONS;
  }
}
