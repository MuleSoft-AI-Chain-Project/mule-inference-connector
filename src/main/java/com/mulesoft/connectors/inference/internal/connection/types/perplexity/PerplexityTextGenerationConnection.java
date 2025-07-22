package com.mulesoft.connectors.inference.internal.connection.types.perplexity;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PerplexityTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String PERPLEXITY_URL = "https://api.perplexity.ai";

  public PerplexityTextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, ParametersDTO parametersDTO) {
    super(httpClient, objectMapper, parametersDTO, fetchApiURL());
  }

  private static String fetchApiURL() {
    return PERPLEXITY_URL + URI_CHAT_COMPLETIONS;
  }
}
