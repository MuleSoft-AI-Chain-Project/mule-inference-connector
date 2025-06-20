package com.mulesoft.connectors.inference.internal.connection.types.lmstudio;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LMStudioTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String LMSTUDIO_URL = "http://localhost:1234/v1";

  public LMStudioTextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName,
                                          String lmStudioBaseURL,
                                          String apiKey, Number temperature, Number topP,
                                          Number maxTokens, Map<String, String> mcpSseServers, int timeout) {
    super(httpClient, objectMapper, apiKey, modelName, maxTokens, temperature, topP, timeout, mcpSseServers,
          fetchApiURL(lmStudioBaseURL));
  }

  private static String fetchApiURL(String lmStudioBaseURL) {
    return lmStudioBaseURL + URI_CHAT_COMPLETIONS;
  }
}
