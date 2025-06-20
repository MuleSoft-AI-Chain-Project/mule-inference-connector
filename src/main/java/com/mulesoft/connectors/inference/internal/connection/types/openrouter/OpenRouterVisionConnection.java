package com.mulesoft.connectors.inference.internal.connection.types.openrouter;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.VisionModelConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;

import com.fasterxml.jackson.databind.ObjectMapper;

public class OpenRouterVisionConnection extends VisionModelConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String OPENROUTER_URL = "https://openrouter.ai/api/v1";

  public OpenRouterVisionConnection(HttpClient httpClient, ObjectMapper objectMapper,
                                    ParametersDTO parametersDTO) {
    super(httpClient, objectMapper, parametersDTO, fetchApiURL());
  }

  private static String fetchApiURL() {
    return OPENROUTER_URL + URI_CHAT_COMPLETIONS;
  }
}
