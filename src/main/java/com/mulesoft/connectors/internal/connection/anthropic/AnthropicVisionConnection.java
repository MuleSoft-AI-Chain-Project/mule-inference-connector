package com.mulesoft.connectors.internal.connection.anthropic;

import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import org.mule.runtime.http.api.client.HttpClient;

import java.util.Map;

public class AnthropicVisionConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/messages";
  public static final String ANTHROPIC_URL = "https://api.anthropic.com/v1";

  public AnthropicVisionConnection(HttpClient httpClient, String modelName, String apiKey,
                                   Number temperature, Number topP,
                                   Number maxTokens, int timeout) {
    super(httpClient, apiKey, modelName, maxTokens, temperature, topP, timeout, null, fetchApiURL(), "ANTHROPIC");
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
    return ANTHROPIC_URL +  URI_CHAT_COMPLETIONS;
  }
} 