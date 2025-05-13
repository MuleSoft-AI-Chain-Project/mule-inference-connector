package com.mulesoft.connectors.internal.connection.xai;

import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import org.mule.runtime.http.api.client.HttpClient;

import java.util.Map;

public class XAIVisionConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String XAI_URL = "https://api.x.ai/v1";

  public XAIVisionConnection(HttpClient httpClient, String modelName, String apiKey,
                             Number temperature, Number topP,
                             Number maxTokens, int timeout) {
    super(httpClient, apiKey, modelName, maxTokens, temperature, topP, timeout, null, fetchApiURL(), "XAI");
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
    return XAI_URL + URI_CHAT_COMPLETIONS;
  }
} 