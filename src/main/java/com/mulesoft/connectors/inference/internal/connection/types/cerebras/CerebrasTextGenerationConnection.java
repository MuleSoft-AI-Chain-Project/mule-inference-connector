package com.mulesoft.connectors.inference.internal.connection.types.cerebras;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CerebrasTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String CEREBRAS_URL = "https://api.cerebras.ai/v1";

  public CerebrasTextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                                          Number temperature, Number topP,
                                          Number maxTokens, Map<String, String> mcpSseServers, int timeout) {
    super(httpClient, objectMapper, apiKey, modelName, maxTokens, temperature, topP, timeout, mcpSseServers, fetchApiURL());
  }

  private static String fetchApiURL() {
    return CEREBRAS_URL + URI_CHAT_COMPLETIONS;
  }
}
