package com.mulesoft.connectors.internal.connection.openai;

import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import org.mule.runtime.http.api.client.HttpClient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class OpenAIVisionConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String OPEN_AI_URL = "https://api.openai.com/v1";
  private final URL connectionURL;

  public OpenAIVisionConnection(HttpClient httpClient, String modelName, String apiKey,
                                Number temperature, Number topP,
                                Number maxTokens, int timeout)
          throws MalformedURLException {
    super( httpClient, apiKey, modelName, maxTokens, temperature, topP, timeout, null,fetchApiURL(),"OPENAI");
    this.connectionURL = new URL(OPEN_AI_URL + URI_CHAT_COMPLETIONS);

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
    return OPEN_AI_URL + URI_CHAT_COMPLETIONS;
  }
}
