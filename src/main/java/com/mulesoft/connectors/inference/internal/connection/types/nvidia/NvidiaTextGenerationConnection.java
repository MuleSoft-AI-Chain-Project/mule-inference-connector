package com.mulesoft.connectors.inference.internal.connection.types.nvidia;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;

import com.fasterxml.jackson.databind.ObjectMapper;

public class NvidiaTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/v1/chat/completions";

  public NvidiaTextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, ParametersDTO parametersDTO,
                                        String nvidiaBaseUrl) {
    super(httpClient, objectMapper, parametersDTO, fetchApiURL(nvidiaBaseUrl));
  }

  private static String fetchApiURL(String nvidiaBaseUrl) {
    return nvidiaBaseUrl + URI_CHAT_COMPLETIONS;
  }
}
