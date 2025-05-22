package com.mulesoft.connectors.inference.internal.connection.anthropic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.helpers.payload.AnthropicRequestPayloadHelper;
import org.mule.runtime.http.api.client.HttpClient;

import java.util.Map;

public class AnthropicTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/messages";
  public static final String ANTHROPIC_URL = "https://api.anthropic.com/v1";

  private AnthropicRequestPayloadHelper requestPayloadHelper;

  public AnthropicTextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                                           Number temperature, Number topP,
                                           Number maxTokens, Map<String, String> mcpSseServers, int timeout) {
    super(httpClient, objectMapper, apiKey, modelName, maxTokens, temperature, topP, timeout, mcpSseServers, fetchApiURL(), "ANTHROPIC");
  }

  @Override
  public AnthropicRequestPayloadHelper getRequestPayloadHelper(){
    if(requestPayloadHelper == null)
      requestPayloadHelper = new AnthropicRequestPayloadHelper(getObjectMapper());
    return requestPayloadHelper;
  }

  @Override
  public Map<String, String> getAdditionalHeaders() {
    return Map.of("x-api-key", this.getApiKey(), "anthropic-version", "2023-06-01");
  }

  private static String fetchApiURL() {
    return ANTHROPIC_URL +  URI_CHAT_COMPLETIONS;
  }
} 