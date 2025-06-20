package com.mulesoft.connectors.inference.internal.connection.types.ollama;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.VisionModelConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;

import com.fasterxml.jackson.databind.ObjectMapper;

public class OllamaVisionConnection extends VisionModelConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat";

  public OllamaVisionConnection(HttpClient httpClient, ObjectMapper objectMapper,
                                ParametersDTO parametersDTO, String ollamaUrl) {
    super(httpClient, objectMapper, parametersDTO, fetchApiURL(ollamaUrl));
  }

  private static String fetchApiURL(String ollamaUrl) {
    return ollamaUrl + URI_CHAT_COMPLETIONS;
  }
}
