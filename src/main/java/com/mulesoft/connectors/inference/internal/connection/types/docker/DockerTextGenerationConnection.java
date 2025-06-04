package com.mulesoft.connectors.inference.internal.connection.types.docker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import org.mule.runtime.http.api.client.HttpClient;

import java.util.Map;

public class DockerTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/engines/llama.cpp/v1/chat/completions";


  public DockerTextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String dockerModelName, String dockerModelUrl, String apiKey,
                                        Number temperature, Number topP,
                                        Number maxTokens, Map<String, String> mcpSseServers, int timeout)
  {
    super(httpClient, objectMapper, apiKey, dockerModelName, maxTokens, temperature, topP, timeout, mcpSseServers,
            fetchApiURL(dockerModelUrl), "DOCKER");
  }

  private static String fetchApiURL(String dockerModelUrl) {
    return dockerModelUrl + URI_CHAT_COMPLETIONS;
  }
} 