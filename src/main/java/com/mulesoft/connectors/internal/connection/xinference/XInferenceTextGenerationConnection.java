package com.mulesoft.connectors.internal.connection.xinference;

import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import org.mule.runtime.http.api.client.HttpClient;

import java.util.Map;

public class XInferenceTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String XINFERENCE_URL = "http://localhost:9997/v1";

  public XInferenceTextGenerationConnection(HttpClient httpClient, String modelName, String xInferenceUrl,
                                            String apiKey, Number temperature, Number topP,
                                            Number maxTokens, Map<String, String> mcpSseServers, int timeout) {
    super(httpClient, apiKey, modelName, maxTokens, temperature, topP, timeout, mcpSseServers, fetchApiURL(xInferenceUrl), "XINFERENCE");
  }

  @Override
  public Map<String, String> getQueryParams() {
    return Map.of();
  }

  @Override
  public Map<String, String> getAdditionalHeaders() {
    return Map.of("Authorization", "Bearer " + this.getApiKey());
  }

  private static String fetchApiURL(String xInferenceUrl) {
    return xInferenceUrl + URI_CHAT_COMPLETIONS;
  }
} 