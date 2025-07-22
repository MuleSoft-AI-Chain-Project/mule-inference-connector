package com.mulesoft.connectors.inference.internal.connection.types.xinference;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;

import com.fasterxml.jackson.databind.ObjectMapper;

public class XInferenceTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";

  public XInferenceTextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper,
                                            ParametersDTO parametersDTO, String xInferenceUrl) {
    super(httpClient, objectMapper, parametersDTO,
          fetchApiURL(xInferenceUrl));
  }

  private static String fetchApiURL(String xInferenceUrl) {
    return xInferenceUrl + URI_CHAT_COMPLETIONS;
  }
}
