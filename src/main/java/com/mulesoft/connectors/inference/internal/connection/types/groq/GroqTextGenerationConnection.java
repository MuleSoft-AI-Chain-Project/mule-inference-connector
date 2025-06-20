package com.mulesoft.connectors.inference.internal.connection.types.groq;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.helpers.payload.GroqRequestPayloadHelper;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GroqTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String GROQ_URL = "https://api.groq.com/openai/v1";
  private GroqRequestPayloadHelper requestPayloadHelper;

  public GroqTextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, ParametersDTO parametersDTO,
                                      Map<String, String> mcpSseServers) {
    super(httpClient, objectMapper, parametersDTO, mcpSseServers, fetchApiURL());
  }

  @Override
  public GroqRequestPayloadHelper getRequestPayloadHelper() {
    if (requestPayloadHelper == null)
      requestPayloadHelper = new GroqRequestPayloadHelper(getObjectMapper());
    return requestPayloadHelper;
  }

  private static String fetchApiURL() {
    return GROQ_URL + URI_CHAT_COMPLETIONS;
  }
}
