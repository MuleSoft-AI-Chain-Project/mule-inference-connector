package com.mulesoft.connectors.inference.internal.connection.types.lmstudio;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LMStudioTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String LMSTUDIO_URL = "http://localhost:1234/v1";

  public LMStudioTextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper,
                                          ParametersDTO parametersDTO, String lmStudioBaseURL,
                                          Map<String, String> mcpSseServers) {
    super(httpClient, objectMapper, parametersDTO, mcpSseServers,
          fetchApiURL(lmStudioBaseURL));
  }

  private static String fetchApiURL(String lmStudioBaseURL) {
    return lmStudioBaseURL + URI_CHAT_COMPLETIONS;
  }
}
