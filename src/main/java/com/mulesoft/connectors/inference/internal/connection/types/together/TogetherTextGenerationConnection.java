package com.mulesoft.connectors.inference.internal.connection.types.together;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TogetherTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String TOGETHER_URL = "https://api.together.xyz/v1";

  public TogetherTextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, ParametersDTO parametersDTO) {
    super(httpClient, objectMapper, parametersDTO, fetchApiURL());
  }

  private static String fetchApiURL() {
    return TOGETHER_URL + URI_CHAT_COMPLETIONS;
  }
}
