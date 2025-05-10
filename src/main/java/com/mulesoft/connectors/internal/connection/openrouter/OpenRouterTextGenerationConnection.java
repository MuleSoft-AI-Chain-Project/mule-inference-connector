package com.mulesoft.connectors.internal.connection.openrouter;

import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import org.mule.runtime.http.api.client.HttpClient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class OpenRouterTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String OPENROUTER_URL ="https://openrouter.ai/api/v1";

  private final URL connectionURL;

  public OpenRouterTextGenerationConnection(HttpClient httpClient, String modelName, String apiKey,
                                            Number temperature, Number topP,
                                            Number maxTokens, Map<String, String> mcpSseServers, int timeout)
          throws MalformedURLException {
    super( httpClient, apiKey, modelName, maxTokens, temperature, topP, timeout, mcpSseServers,fetchApiURL(),"OPENROUTER");
    this.connectionURL = new URL(OPENROUTER_URL + URI_CHAT_COMPLETIONS);
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
    return OPENROUTER_URL + URI_CHAT_COMPLETIONS;
  }
}
