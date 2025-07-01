package com.mulesoft.connectors.inference.internal.connection.types.ollama;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.helpers.payload.OllamaRequestPayloadHelper;
import com.mulesoft.connectors.inference.internal.helpers.response.OllamaHttpResponseHelper;
import com.mulesoft.connectors.inference.internal.helpers.response.mapper.OllamaResponseMapper;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class OllamaTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat";

  private OllamaRequestPayloadHelper requestPayloadHelper;
  private OllamaHttpResponseHelper httpResponseHelper;
  private OllamaResponseMapper responseMapper;

  public OllamaTextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper,
                                        ParametersDTO parametersDTO, String ollamaUrl,
                                        Map<String, String> mcpSseServers) {
    super(httpClient, objectMapper, parametersDTO, mcpSseServers,
          fetchApiURL(ollamaUrl));
  }

  @Override
  public OllamaRequestPayloadHelper getRequestPayloadHelper() {
    if (requestPayloadHelper == null)
      requestPayloadHelper = new OllamaRequestPayloadHelper(getObjectMapper());
    return requestPayloadHelper;
  }

  @Override
  public OllamaHttpResponseHelper getResponseHelper() {
    if (httpResponseHelper == null)
      httpResponseHelper = new OllamaHttpResponseHelper(getObjectMapper());
    return httpResponseHelper;
  }

  @Override
  public OllamaResponseMapper getResponseMapper() {
    if (responseMapper == null)
      responseMapper = new OllamaResponseMapper(this.getObjectMapper());
    return responseMapper;
  }

  private static String fetchApiURL(String ollamaUrl) {
    return ollamaUrl + URI_CHAT_COMPLETIONS;
  }
}
