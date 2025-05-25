package com.mulesoft.connectors.inference.internal.connection.anthropic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.VisionModelConnection;
import org.mule.runtime.http.api.client.HttpClient;

public class AnthropicVisionConnection extends VisionModelConnection {

  private static final String URI_CHAT_COMPLETIONS = "/messages";
  public static final String ANTHROPIC_URL = "https://api.anthropic.com/v1";

  public AnthropicVisionConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                                   Number temperature, Number topP,
                                   Number maxTokens, int timeout) {
    super(httpClient, objectMapper, apiKey, modelName, maxTokens, temperature, topP, timeout, fetchApiURL(), "ANTHROPIC");
  }

  private static String fetchApiURL() {
    return ANTHROPIC_URL +  URI_CHAT_COMPLETIONS;
  }
} 