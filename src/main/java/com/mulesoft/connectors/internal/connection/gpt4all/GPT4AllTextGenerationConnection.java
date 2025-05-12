package com.mulesoft.connectors.internal.connection.gpt4all;

import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import org.mule.runtime.http.api.client.HttpClient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class GPT4AllTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";

  private final URL connectionURL;

  public GPT4AllTextGenerationConnection(HttpClient httpClient, String modelName, String gpt4AllBaseURL, String apiKey,
                                         Number temperature, Number topP,
                                         Number maxTokens, Map<String, String> mcpSseServers, int timeout)
          throws MalformedURLException {
    super(httpClient, apiKey, modelName, maxTokens, temperature, topP, timeout, mcpSseServers, fetchApiURL(gpt4AllBaseURL), "GPT4ALL");
    this.connectionURL = new URL(fetchApiURL(gpt4AllBaseURL));
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

  private static String fetchApiURL(String gpt4AllBaseURL) {
    return gpt4AllBaseURL + URI_CHAT_COMPLETIONS;
  }
} 