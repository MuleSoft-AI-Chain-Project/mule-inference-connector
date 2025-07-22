package com.mulesoft.connectors.inference.internal.connection.types.deepseek;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DeepseekTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String DEEPSEEK_URL = "https://api.deepseek.com";

  public DeepseekTextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, ParametersDTO parametersDTO) {
    super(httpClient, objectMapper, parametersDTO, fetchApiURL());
  }

  private static String fetchApiURL() {
    return DEEPSEEK_URL + URI_CHAT_COMPLETIONS;
  }
}
