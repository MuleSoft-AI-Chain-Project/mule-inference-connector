package com.mulesoft.connectors.inference.internal.connection.types.xai;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.types.VisionModelConnection;
import org.mule.runtime.http.api.client.HttpClient;


public class XAIVisionConnection extends VisionModelConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String XAI_URL = "https://api.x.ai/v1";

  public XAIVisionConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                             Number temperature, Number topP,
                             Number maxTokens, int timeout) {
    super(httpClient, objectMapper, apiKey, modelName, maxTokens, temperature, topP, timeout, fetchApiURL(), "XAI");
  }

  private static String fetchApiURL() {
    return XAI_URL + URI_CHAT_COMPLETIONS;
  }
} 