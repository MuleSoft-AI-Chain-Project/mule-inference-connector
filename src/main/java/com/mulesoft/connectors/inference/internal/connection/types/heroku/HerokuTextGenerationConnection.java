package com.mulesoft.connectors.inference.internal.connection.types.heroku;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HerokuTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/v1/chat/completions";

  public HerokuTextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper,
                                        ParametersDTO parametersDTO, String herokuInferenceUrl,
                                        Map<String, String> mcpSseServers) {
    super(httpClient, objectMapper, parametersDTO, mcpSseServers,
          fetchApiURL(herokuInferenceUrl));
  }

  private static String fetchApiURL(String herokuInferenceUrl) {
    return herokuInferenceUrl + URI_CHAT_COMPLETIONS;
  }
}
