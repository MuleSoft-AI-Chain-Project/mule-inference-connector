package com.mulesoft.connectors.internal.connection.portkey;

import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import org.mule.runtime.http.api.client.HttpClient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class PortkeyVisionConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String PORTKEY_URL = "https://api.portkey.ai/v1";

  private final URL connectionURL;

  public PortkeyVisionConnection(HttpClient httpClient, String modelName, String apiKey,
                                 Number temperature, Number topP,
                                 Number maxTokens, int timeout)
          throws MalformedURLException {
    super(httpClient, apiKey, modelName, maxTokens, temperature, topP, timeout, null, fetchApiURL(), "PORTKEY");
    this.connectionURL = new URL(PORTKEY_URL + URI_CHAT_COMPLETIONS);
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
    return PORTKEY_URL + URI_CHAT_COMPLETIONS;
  }
} 