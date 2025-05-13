package com.mulesoft.connectors.internal.connection.github;

import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import org.mule.runtime.http.api.client.HttpClient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class GithubVisionConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String GITHUB_URL = "https://models.inference.ai.azure.com";

  private final URL connectionURL;

  public GithubVisionConnection(HttpClient httpClient, String modelName, String apiKey,
                                Number temperature, Number topP,
                                Number maxTokens, int timeout)
          throws MalformedURLException {
    super(httpClient, apiKey, modelName, maxTokens, temperature, topP, timeout, null, fetchApiURL(), "GITHUB");
    this.connectionURL = new URL(GITHUB_URL + URI_CHAT_COMPLETIONS);
  }

  @Override
  public URL getConnectionURL() {
    return connectionURL;
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