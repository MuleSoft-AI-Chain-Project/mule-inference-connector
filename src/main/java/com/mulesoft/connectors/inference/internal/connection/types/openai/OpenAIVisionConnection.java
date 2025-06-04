package com.mulesoft.connectors.inference.internal.connection.types.openai;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.types.VisionModelConnection;
import org.mule.runtime.http.api.client.HttpClient;


public class OpenAIVisionConnection extends VisionModelConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String OPEN_AI_URL = "https://api.openai.com/v1";

  public OpenAIVisionConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                                Number temperature, Number topP,
                                Number maxTokens, int timeout) {
    super( httpClient, objectMapper, apiKey, modelName, maxTokens, temperature, topP, timeout, fetchApiURL(),"OPENAI");
  }

  private static String fetchApiURL() {
    return OPEN_AI_URL + URI_CHAT_COMPLETIONS;
  }
}
