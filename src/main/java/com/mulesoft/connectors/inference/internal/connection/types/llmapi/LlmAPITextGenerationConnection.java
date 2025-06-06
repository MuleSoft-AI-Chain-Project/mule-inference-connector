package com.mulesoft.connectors.inference.internal.connection.types.llmapi;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LlmAPITextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String LLAMAAPI_URL = "https://api.llmapi.com";

  public LlmAPITextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                                        Number temperature, Number topP,
                                        Number maxTokens, Map<String, String> mcpSseServers, int timeout) {
    super(httpClient, objectMapper, apiKey, modelName, maxTokens, temperature, topP, timeout, mcpSseServers, fetchApiURL(),
          "LLAMAAPI");
  }

  private static String fetchApiURL() {
    return LLAMAAPI_URL + URI_CHAT_COMPLETIONS;
  }
}
