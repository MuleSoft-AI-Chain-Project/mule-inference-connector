package com.mulesoft.connectors.inference.internal.connection.types.groq;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.VisionModelConnection;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GroqVisionConnection extends VisionModelConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String GROQ_URL = "https://api.groq.com/openai/v1";

  public GroqVisionConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                              Number temperature, Number topP,
                              Number maxTokens, int timeout) {
    super(httpClient, objectMapper, apiKey, modelName, maxTokens, temperature, topP, timeout, fetchApiURL(), "GROQ");
  }

  private static String fetchApiURL() {
    return GROQ_URL + URI_CHAT_COMPLETIONS;
  }
}
