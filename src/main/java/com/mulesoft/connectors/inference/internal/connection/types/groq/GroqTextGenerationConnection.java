package com.mulesoft.connectors.inference.internal.connection.types.groq;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.helpers.payload.GroqRequestPayloadHelper;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GroqTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String GROQ_URL = "https://api.groq.com/openai/v1";
  private GroqRequestPayloadHelper requestPayloadHelper;

  public GroqTextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                                      Number temperature, Number topP,
                                      Number maxTokens, Map<String, String> mcpSseServers, int timeout) {
    super(httpClient, objectMapper, apiKey, modelName, maxTokens, temperature, topP, timeout, mcpSseServers, fetchApiURL(),
          "GROQ");
  }

  @Override
  public GroqRequestPayloadHelper getRequestPayloadHelper() {
    if (requestPayloadHelper == null)
      requestPayloadHelper = new GroqRequestPayloadHelper(getObjectMapper());
    return requestPayloadHelper;
  }

  private static String fetchApiURL() {
    return GROQ_URL + URI_CHAT_COMPLETIONS;
  }
}
