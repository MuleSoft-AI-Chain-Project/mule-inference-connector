package com.mulesoft.connectors.internal.connection.openaicompatible;

import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import org.mule.runtime.http.api.client.HttpClient;

import java.util.Map;

public class OpenAICompatibleTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String OPENAI_COMPATIBLE_ENDPOINT = "https://server.endpoint.com";

  public OpenAICompatibleTextGenerationConnection(HttpClient httpClient, String modelName, String openAICompatibleURL,
                                                  String apiKey, Number temperature, Number topP,
                                                  Number maxTokens, Map<String, String> mcpSseServers, int timeout) {
    super( httpClient, apiKey, modelName, maxTokens, temperature, topP, timeout, mcpSseServers,fetchApiURL(openAICompatibleURL),"OPENAI_COMPATIBLE_ENDPOINT");
  }

  @Override
  public Map<String, String> getQueryParams() {
    return Map.of();
  }

  @Override
  public Map<String, String> getAdditionalHeaders() {
    return Map.of("Authorization", "Bearer " + this.getApiKey());
  }

  private static String fetchApiURL(String openAICompatibleURL) {
    return openAICompatibleURL + URI_CHAT_COMPLETIONS;
  }
}
