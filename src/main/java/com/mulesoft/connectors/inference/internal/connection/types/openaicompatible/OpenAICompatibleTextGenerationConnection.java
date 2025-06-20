package com.mulesoft.connectors.inference.internal.connection.types.openaicompatible;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class OpenAICompatibleTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String OPENAI_COMPATIBLE_ENDPOINT = "https://server.endpoint.com";

  public OpenAICompatibleTextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName,
                                                  String openAICompatibleURL,
                                                  String apiKey, Number temperature, Number topP,
                                                  Number maxTokens, Map<String, String> mcpSseServers, int timeout) {
    super(httpClient, objectMapper, apiKey, modelName, maxTokens, temperature, topP, timeout, mcpSseServers,
          fetchApiURL(openAICompatibleURL));
  }

  private static String fetchApiURL(String openAICompatibleURL) {
    return openAICompatibleURL + URI_CHAT_COMPLETIONS;
  }
}
