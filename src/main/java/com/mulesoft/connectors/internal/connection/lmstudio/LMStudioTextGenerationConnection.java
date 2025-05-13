package com.mulesoft.connectors.internal.connection.lmstudio;

import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import org.mule.runtime.http.api.client.HttpClient;

import java.util.Map;

public class LMStudioTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String LMSTUDIO_URL = "http://localhost:1234/v1";

  public LMStudioTextGenerationConnection(HttpClient httpClient, String modelName, String lmStudioBaseURL,
                                          String apiKey, Number temperature, Number topP,
                                          Number maxTokens, Map<String, String> mcpSseServers, int timeout) {
    super(httpClient, apiKey, modelName, maxTokens, temperature, topP, timeout, mcpSseServers, fetchApiURL(lmStudioBaseURL), "LMSTUDIO");
  }

  @Override
  public Map<String, String> getQueryParams() {
    return Map.of();
  }

  @Override
  public Map<String, String> getAdditionalHeaders() {
    return Map.of("Authorization", "Bearer " + this.getApiKey());
  }

  private static String fetchApiURL(String lmStudioBaseURL) {
    return lmStudioBaseURL + URI_CHAT_COMPLETIONS;
  }
} 