package com.mulesoft.connectors.inference.internal.connection.types.gpt4all;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GPT4AllTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";

  public GPT4AllTextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName,
                                         String gpt4AllBaseURL, String apiKey,
                                         Number temperature, Number topP,
                                         Number maxTokens, Map<String, String> mcpSseServers, int timeout) {
    super(httpClient, objectMapper, apiKey, modelName, maxTokens, temperature, topP, timeout, mcpSseServers,
          fetchApiURL(gpt4AllBaseURL), "GPT4ALL");
  }

  private static String fetchApiURL(String gpt4AllBaseURL) {
    return gpt4AllBaseURL + URI_CHAT_COMPLETIONS;
  }
}
