package com.mulesoft.connectors.inference.internal.connection.openrouter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import org.mule.runtime.http.api.client.HttpClient;

import java.util.Map;

public class OpenRouterTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String OPENROUTER_URL = "https://openrouter.ai/api/v1";

  public OpenRouterTextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                                            Number temperature, Number topP,
                                            Number maxTokens, Map<String, String> mcpSseServers, int timeout) {
    super(httpClient, objectMapper, apiKey, modelName, maxTokens, temperature, topP, timeout, mcpSseServers, fetchApiURL(), "OPENROUTER");
  }

  private static String fetchApiURL() {
    return OPENROUTER_URL + URI_CHAT_COMPLETIONS;
  }
}
