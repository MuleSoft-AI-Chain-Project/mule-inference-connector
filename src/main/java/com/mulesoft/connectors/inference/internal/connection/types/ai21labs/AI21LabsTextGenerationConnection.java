package com.mulesoft.connectors.inference.internal.connection.types.ai21labs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import org.mule.runtime.http.api.client.HttpClient;

import java.util.Map;

public class AI21LabsTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String AI21LABS_URL = "https://api.ai21.com/studio/v1";

  public AI21LabsTextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                                          Number temperature, Number topP,
                                          Number maxTokens, Map<String, String> mcpSseServers, int timeout) {
    super(httpClient, objectMapper, apiKey, modelName, maxTokens, temperature, topP, timeout, mcpSseServers, fetchApiURL(),
            "AI21LABS");
  }

  private static String fetchApiURL() {
    return AI21LABS_URL + URI_CHAT_COMPLETIONS;
  }
} 