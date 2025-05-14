package com.mulesoft.connectors.internal.connection.github;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import org.mule.runtime.http.api.client.HttpClient;

import java.util.Map;

public class GithubVisionConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String GITHUB_URL = "https://models.inference.ai.azure.com";

  public GithubVisionConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                                Number temperature, Number topP,
                                Number maxTokens, int timeout) {
    super(httpClient, objectMapper, apiKey, modelName, maxTokens, temperature, topP, timeout, null, fetchApiURL(), "GITHUB");
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
    return GITHUB_URL + URI_CHAT_COMPLETIONS;
  }
} 