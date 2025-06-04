package com.mulesoft.connectors.inference.internal.connection.types.portkey;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.types.VisionModelConnection;
import org.mule.runtime.http.api.client.HttpClient;


public class PortkeyVisionConnection extends VisionModelConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String PORTKEY_URL = "https://api.portkey.ai/v1";

  public PortkeyVisionConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                                 Number temperature, Number topP,
                                 Number maxTokens, int timeout) {
    super(httpClient, objectMapper, apiKey, modelName, maxTokens, temperature, topP, timeout, fetchApiURL(), "PORTKEY");
  }

  private static String fetchApiURL() {
    return PORTKEY_URL + URI_CHAT_COMPLETIONS;
  }
} 