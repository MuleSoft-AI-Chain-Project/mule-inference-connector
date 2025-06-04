package com.mulesoft.connectors.inference.internal.connection.types.xinference;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class XInferenceTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String XINFERENCE_URL = "http://localhost:9997/v1";

  public XInferenceTextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName,
                                            String xInferenceUrl,
                                            String apiKey, Number temperature, Number topP,
                                            Number maxTokens, Map<String, String> mcpSseServers, int timeout) {
    super(httpClient, objectMapper, apiKey, modelName, maxTokens, temperature, topP, timeout, mcpSseServers,
          fetchApiURL(xInferenceUrl), "XINFERENCE");
  }

  private static String fetchApiURL(String xInferenceUrl) {
    return xInferenceUrl + URI_CHAT_COMPLETIONS;
  }
}
