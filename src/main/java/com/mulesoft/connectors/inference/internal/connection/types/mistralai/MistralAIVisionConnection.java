package com.mulesoft.connectors.inference.internal.connection.types.mistralai;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.VisionModelConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MistralAIVisionConnection extends VisionModelConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String MISTRAL_AI_URL = "https://api.mistral.ai/v1";

  public MistralAIVisionConnection(HttpClient httpClient, ObjectMapper objectMapper,
                                   ParametersDTO parametersDTO) {
    super(httpClient, objectMapper, parametersDTO, fetchApiURL());
  }

  private static String fetchApiURL() {
    return MISTRAL_AI_URL + URI_CHAT_COMPLETIONS;
  }
}
