package com.mulesoft.connectors.internal.connection.fireworks;

import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.internal.constants.InferenceConstants;
import org.mule.runtime.http.api.client.HttpClient;

import java.util.Map;

public class FireworksTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String FIREWORKS_URL = "https://api.fireworks.ai/inference/v1";

  public FireworksTextGenerationConnection(HttpClient httpClient, String modelName, String apiKey,
                                         Number temperature, Number topP,
                                         Number maxTokens, Map<String, String> mcpSseServers, int timeout) {
    super(httpClient, apiKey, modelName, maxTokens, temperature, topP, timeout, mcpSseServers, fetchApiURL(), "FIREWORKS");
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
    return FIREWORKS_URL + URI_CHAT_COMPLETIONS;
  }
} 