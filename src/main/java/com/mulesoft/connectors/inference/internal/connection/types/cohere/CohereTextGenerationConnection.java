package com.mulesoft.connectors.inference.internal.connection.types.cohere;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CohereTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat";
  public static final String COHERE_URL = "https://api.cohere.com/v2";

  public CohereTextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                                        Number temperature, Number topP,
                                        Number maxTokens, Map<String, String> mcpSseServers, int timeout) {
    super(httpClient, objectMapper, apiKey, modelName, maxTokens, temperature, topP, timeout, mcpSseServers, fetchApiURL(),
          "COHERE");
  }

  private static String fetchApiURL() {
    return COHERE_URL + URI_CHAT_COMPLETIONS;
  }
}
