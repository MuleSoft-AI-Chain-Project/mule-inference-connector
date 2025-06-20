package com.mulesoft.connectors.inference.internal.connection.types.xinference;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class XInferenceTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String XINFERENCE_URL = "http://localhost:9997/v1";

  public XInferenceTextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper,
                                            ParametersDTO parametersDTO, String xInferenceUrl,
                                            Map<String, String> mcpSseServers) {
    super(httpClient, objectMapper, parametersDTO, mcpSseServers,
          fetchApiURL(xInferenceUrl));
  }

  private static String fetchApiURL(String xInferenceUrl) {
    return xInferenceUrl + URI_CHAT_COMPLETIONS;
  }
}
