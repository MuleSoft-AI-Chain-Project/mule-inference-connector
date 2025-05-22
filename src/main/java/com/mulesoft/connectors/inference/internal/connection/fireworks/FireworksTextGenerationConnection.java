package com.mulesoft.connectors.inference.internal.connection.fireworks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import org.mule.runtime.http.api.client.HttpClient;

import java.util.Map;

public class FireworksTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String FIREWORKS_URL = "https://api.fireworks.ai/inference/v1";

  public FireworksTextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                                           Number temperature, Number topP,
                                           Number maxTokens, Map<String, String> mcpSseServers, int timeout) {
    super(httpClient, objectMapper, apiKey, modelName, maxTokens, temperature, topP, timeout, mcpSseServers, fetchApiURL(), "FIREWORKS");
  }

  private static String fetchApiURL() {
    return FIREWORKS_URL + URI_CHAT_COMPLETIONS;
  }
} 