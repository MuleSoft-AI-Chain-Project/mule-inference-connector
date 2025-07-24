package com.mulesoft.connectors.inference.internal.connection.types.nvidia;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;

import com.fasterxml.jackson.databind.ObjectMapper;

public class NvidiaTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String NVIDIA_URL = "https://integrate.api.nvidia.com/v1";

  public NvidiaTextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, ParametersDTO parametersDTO) {
    super(httpClient, objectMapper, parametersDTO, fetchApiURL());
  }

  private static String fetchApiURL() {
    return NVIDIA_URL + URI_CHAT_COMPLETIONS;
  }
}
