package com.mulesoft.connectors.inference.internal.connection.openrouter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import org.mule.runtime.http.api.client.HttpClient;

import java.util.Map;

public class OpenRouterVisionConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String OPENROUTER_URL ="https://openrouter.ai/api/v1";

  public OpenRouterVisionConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                                    Number temperature, Number topP,
                                    Number maxTokens, int timeout) {
    super( httpClient, objectMapper, apiKey, modelName, maxTokens, temperature, topP, timeout, null,fetchApiURL(),"OPENAI");
  }

  @Override
  public Map<String, String> getQueryParams() {
    return Map.of();
  }

  @Override
  public Map<String, String> getAdditionalHeaders() {
    return Map.of("Authorization", "Bearer " + this.getApiKey());
  }

  private static String fetchApiURL() {
    return OPENROUTER_URL + URI_CHAT_COMPLETIONS;
  }
}
