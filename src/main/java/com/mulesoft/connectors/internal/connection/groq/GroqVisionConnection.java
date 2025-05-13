package com.mulesoft.connectors.internal.connection.groq;

import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import org.mule.runtime.http.api.client.HttpClient;

import java.util.Map;

public class GroqVisionConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String GROQ_URL = "https://api.groq.com/openai/v1";

  public GroqVisionConnection(HttpClient httpClient, String modelName, String apiKey,
                              Number temperature, Number topP,
                              Number maxTokens,  int timeout) {
    super(httpClient, apiKey, modelName, maxTokens, temperature, topP, timeout, null, fetchApiURL(), "GROQ");
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
    return GROQ_URL + URI_CHAT_COMPLETIONS;
  }
} 