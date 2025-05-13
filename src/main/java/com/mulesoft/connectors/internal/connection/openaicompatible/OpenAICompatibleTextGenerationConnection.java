package com.mulesoft.connectors.internal.connection.openaicompatible;

import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import org.mule.runtime.http.api.client.HttpClient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class OpenAICompatibleTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String OPENAI_COMPATIBLE_ENDPOINT = "https://server.endpoint.com";

  private final URL connectionURL;

  public OpenAICompatibleTextGenerationConnection(HttpClient httpClient, String modelName, String openAICompatibleURL,
                                                  String apiKey, Number temperature, Number topP,
                                                  Number maxTokens, Map<String, String> mcpSseServers, int timeout)
          throws MalformedURLException {
    super( httpClient, apiKey, modelName, maxTokens, temperature, topP, timeout, mcpSseServers,fetchApiURL(openAICompatibleURL),"OPENAI_COMPATIBLE_ENDPOINT");
    this.connectionURL = new URL(fetchApiURL(openAICompatibleURL));
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

  private static String fetchApiURL(String openAICompatibleURL) {
    return openAICompatibleURL + URI_CHAT_COMPLETIONS;
  }
}
