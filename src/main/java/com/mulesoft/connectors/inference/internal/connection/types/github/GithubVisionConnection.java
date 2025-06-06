package com.mulesoft.connectors.inference.internal.connection.types.github;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.VisionModelConnection;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GithubVisionConnection extends VisionModelConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String GITHUB_URL = "https://models.inference.ai.azure.com";

  public GithubVisionConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                                Number temperature, Number topP,
                                Number maxTokens, int timeout) {
    super(httpClient, objectMapper, apiKey, modelName, maxTokens, temperature, topP, timeout, fetchApiURL(), "GITHUB");
  }

  private static String fetchApiURL() {
    return GITHUB_URL + URI_CHAT_COMPLETIONS;
  }
}
