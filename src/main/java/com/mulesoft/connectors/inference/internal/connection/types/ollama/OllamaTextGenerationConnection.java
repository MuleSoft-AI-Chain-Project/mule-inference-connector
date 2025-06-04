package com.mulesoft.connectors.inference.internal.connection.types.ollama;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.helpers.payload.OllamaRequestPayloadHelper;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class OllamaTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat";

  private OllamaRequestPayloadHelper requestPayloadHelper;

  public OllamaTextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String ollamaUrl,
                                        String apiKey, Number temperature, Number topP,
                                        Number maxTokens, Map<String, String> mcpSseServers, int timeout) {
    super(httpClient, objectMapper, apiKey, modelName, maxTokens, temperature, topP, timeout, mcpSseServers,
          fetchApiURL(ollamaUrl), "OLLAMA");
  }

  @Override
  public OllamaRequestPayloadHelper getRequestPayloadHelper() {
    if (requestPayloadHelper == null)
      requestPayloadHelper = new OllamaRequestPayloadHelper(getObjectMapper());
    return requestPayloadHelper;
  }

  private static String fetchApiURL(String ollamaUrl) {
    return ollamaUrl + URI_CHAT_COMPLETIONS;
  }
}
