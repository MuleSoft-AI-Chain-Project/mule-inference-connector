package com.mulesoft.connectors.inference.internal.connection.openrouter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.VisionModelConnection;
import org.mule.runtime.http.api.client.HttpClient;


public class OpenRouterVisionConnection extends VisionModelConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String OPENROUTER_URL ="https://openrouter.ai/api/v1";

  public OpenRouterVisionConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                                    Number temperature, Number topP,
                                    Number maxTokens, int timeout) {
    super( httpClient, objectMapper, apiKey, modelName, maxTokens, temperature, topP, timeout, fetchApiURL(),"OPENAI");
  }

  private static String fetchApiURL() {
    return OPENROUTER_URL + URI_CHAT_COMPLETIONS;
  }
}
