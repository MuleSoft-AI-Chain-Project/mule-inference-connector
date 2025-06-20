package com.mulesoft.connectors.inference.internal.connection.types.openai;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.helpers.payload.OpenAIRequestPayloadHelper;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class OpenAITextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String OPENAI_URL = "https://api.openai.com/v1";
  private OpenAIRequestPayloadHelper requestPayloadHelper;

  public OpenAITextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, ParametersDTO parametersDTO,
                                        Map<String, String> mcpSseServers) {
    super(httpClient, objectMapper, parametersDTO, mcpSseServers, fetchApiURL());
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
