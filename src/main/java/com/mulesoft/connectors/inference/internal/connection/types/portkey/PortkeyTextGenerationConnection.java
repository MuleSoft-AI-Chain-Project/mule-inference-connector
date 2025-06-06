package com.mulesoft.connectors.inference.internal.connection.types.portkey;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PortkeyTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String PORTKEY_URL = "https://api.portkey.ai/v1";

  private final String virtualKey;

  public PortkeyTextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName,
                                         String virtualKey, String apiKey,
                                         Number temperature, Number topP,
                                         Number maxTokens, Map<String, String> mcpSseServers, int timeout) {
    super(httpClient, objectMapper, apiKey, modelName, maxTokens, temperature, topP, timeout, mcpSseServers, fetchApiURL(),
          "PORTKEY");
    this.virtualKey = virtualKey;
  }

  @Override
  public Map<String, String> getAdditionalHeaders() {
    return Map.of("x-portkey-api-key", this.getApiKey(), "x-portkey-virtual-key", this.virtualKey);
  }

  private static String fetchApiURL() {
    return PORTKEY_URL + URI_CHAT_COMPLETIONS;
  }
}
