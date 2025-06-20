package com.mulesoft.connectors.inference.internal.connection.types.docker;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DockerTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/engines/llama.cpp/v1/chat/completions";


  public DockerTextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper,
                                        ParametersDTO parametersDTO, String dockerModelUrl,
                                        Map<String, String> mcpSseServers) {
    super(httpClient, objectMapper, parametersDTO, mcpSseServers,
          fetchApiURL(dockerModelUrl));
  }

  private static String fetchApiURL(String dockerModelUrl) {
    return dockerModelUrl + URI_CHAT_COMPLETIONS;
  }
}
