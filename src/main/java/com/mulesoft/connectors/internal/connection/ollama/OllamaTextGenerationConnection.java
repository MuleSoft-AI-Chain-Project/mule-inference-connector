package com.mulesoft.connectors.internal.connection.ollama;

import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import org.mule.runtime.http.api.client.HttpClient;

import java.util.Map;

public class OllamaTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat";

  public OllamaTextGenerationConnection(HttpClient httpClient, String modelName,String ollamaUrl,
                                        String apiKey, Number temperature, Number topP,
                                        Number maxTokens, Map<String, String> mcpSseServers, int timeout)
  {
    super(httpClient, apiKey, modelName, maxTokens, temperature, topP, timeout, mcpSseServers, fetchApiURL(ollamaUrl), "OLLAMA");
  }

  @Override
  public Map<String, String> getQueryParams() {
    return Map.of();
  }

  @Override
  public Map<String, String> getAdditionalHeaders() {
    return Map.of("Authorization", "Bearer " + this.getApiKey());
  }

  private static String fetchApiURL(String ollamaUrl) {
    return ollamaUrl + URI_CHAT_COMPLETIONS;
  }
} 