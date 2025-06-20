package com.mulesoft.connectors.inference.internal.connection.types.ai21labs;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AI21LabsTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String AI21LABS_URL = "https://api.ai21.com/studio/v1";

  public AI21LabsTextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, ParametersDTO parametersDTO,
                                          Map<String, String> mcpSseServers) {
    super(httpClient, objectMapper, parametersDTO, mcpSseServers, fetchApiURL());
  }

  private static String fetchApiURL() {
    return AI21LABS_URL + URI_CHAT_COMPLETIONS;
  }
}
