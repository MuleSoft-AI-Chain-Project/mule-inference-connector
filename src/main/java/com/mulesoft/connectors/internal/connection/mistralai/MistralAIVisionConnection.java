package com.mulesoft.connectors.internal.connection.mistralai;

import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import org.mule.runtime.http.api.client.HttpClient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class MistralAIVisionConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String MISTRAL_AI_URL = "https://api.mistral.ai/v1";

  private final URL connectionURL;

  public MistralAIVisionConnection(HttpClient httpClient, String modelName, String apiKey,
                                   Number temperature, Number topP,
                                   Number maxTokens, int timeout)
          throws MalformedURLException {
    super( httpClient, apiKey, modelName, maxTokens, temperature, topP, timeout, null,fetchApiURL(),"MistralAI");
    this.connectionURL = new URL(MISTRAL_AI_URL + URI_CHAT_COMPLETIONS);

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
    return MISTRAL_AI_URL + URI_CHAT_COMPLETIONS;
  }
}
