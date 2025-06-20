package com.mulesoft.connectors.inference.internal.connection.types.gpt4all;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GPT4AllTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";

  public GPT4AllTextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper,
                                         ParametersDTO parametersDTO, String gpt4AllBaseURL,
                                         Map<String, String> mcpSseServers) {
    super(httpClient, objectMapper, parametersDTO, mcpSseServers,
          fetchApiURL(gpt4AllBaseURL));
  }

  private static String fetchApiURL(String gpt4AllBaseURL) {
    return gpt4AllBaseURL + URI_CHAT_COMPLETIONS;
  }
}
