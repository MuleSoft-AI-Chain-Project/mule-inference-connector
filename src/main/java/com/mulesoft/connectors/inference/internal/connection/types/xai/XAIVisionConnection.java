package com.mulesoft.connectors.inference.internal.connection.types.xai;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.VisionModelConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;

import com.fasterxml.jackson.databind.ObjectMapper;

public class XAIVisionConnection extends VisionModelConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String XAI_URL = "https://api.x.ai/v1";

  public XAIVisionConnection(HttpClient httpClient, ObjectMapper objectMapper,
                             ParametersDTO parametersDTO) {
    super(httpClient, objectMapper, parametersDTO, fetchApiURL());
  }

  private static String fetchApiURL() {
    return XAI_URL + URI_CHAT_COMPLETIONS;
  }
}
