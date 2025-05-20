package com.mulesoft.connectors.inference.internal.connection.groq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.helpers.payload.GroqRequestPayloadHelper;
import org.mule.runtime.http.api.client.HttpClient;

import java.util.Map;

public class GroqTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String GROQ_URL = "https://api.groq.com/openai/v1";
  private GroqRequestPayloadHelper requestPayloadHelper;

  public GroqTextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                                      Number temperature, Number topP,
                                      Number maxTokens, Map<String, String> mcpSseServers, int timeout) {
    super(httpClient, objectMapper, apiKey, modelName, maxTokens, temperature, topP, timeout, mcpSseServers, fetchApiURL(), "GROQ");
  }

  @Override
  public GroqRequestPayloadHelper getRequestPayloadHelper(){
    if(requestPayloadHelper == null)
      requestPayloadHelper = new GroqRequestPayloadHelper(getObjectMapper());
    return requestPayloadHelper;
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
    return GROQ_URL + URI_CHAT_COMPLETIONS;
  }
} 