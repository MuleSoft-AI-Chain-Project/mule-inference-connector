package com.mulesoft.connectors.inference.internal.connection.types.openai;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.helpers.payload.OpenAIRequestPayloadHelper;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class OpenAITextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String OPENAI_URL = "https://api.openai.com/v1";
  private OpenAIRequestPayloadHelper requestPayloadHelper;

  public OpenAITextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                                        Number temperature, Number topP,
                                        Number maxTokens, Map<String, String> mcpSseServers, int timeout) {
    super(httpClient, objectMapper, apiKey, modelName, maxTokens, temperature, topP, timeout, mcpSseServers, fetchApiURL());
  }

  @Override
  public OpenAIRequestPayloadHelper getRequestPayloadHelper() {
    if (requestPayloadHelper == null)
      requestPayloadHelper = new OpenAIRequestPayloadHelper(getObjectMapper());
    return requestPayloadHelper;
  }

  private static String fetchApiURL() {
    return OPENAI_URL + URI_CHAT_COMPLETIONS;
  }
}
