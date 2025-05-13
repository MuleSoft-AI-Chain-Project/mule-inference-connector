package com.mulesoft.connectors.internal.connection.ollama;

import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import org.mule.runtime.http.api.client.HttpClient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class OllamaVisionConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat";

  private final URL connectionURL;

  public OllamaVisionConnection(HttpClient httpClient, String modelName, String ollamaUrl,
                                String apiKey, Number temperature, Number topP,
                                Number maxTokens, int timeout)
          throws MalformedURLException {
    super(httpClient, apiKey, modelName, maxTokens, temperature, topP, timeout, null, fetchApiURL(ollamaUrl), "OLLAMA");
    this.connectionURL = new URL(fetchApiURL(ollamaUrl));
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

  private static String fetchApiURL(String ollamaUrl) {
    return ollamaUrl + URI_CHAT_COMPLETIONS;
  }
} 