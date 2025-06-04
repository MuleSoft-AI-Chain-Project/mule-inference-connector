package com.mulesoft.connectors.inference.internal.connection.types.ollama;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.types.VisionModelConnection;
import org.mule.runtime.http.api.client.HttpClient;

public class OllamaVisionConnection extends VisionModelConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat";

  public OllamaVisionConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String ollamaUrl,
                                String apiKey, Number temperature, Number topP,
                                Number maxTokens, int timeout)
  {
    super(httpClient, objectMapper, apiKey, modelName, maxTokens, temperature, topP, timeout,  fetchApiURL(ollamaUrl), "OLLAMA");
  }

  private static String fetchApiURL(String ollamaUrl) {
    return ollamaUrl + URI_CHAT_COMPLETIONS;
  }
} 