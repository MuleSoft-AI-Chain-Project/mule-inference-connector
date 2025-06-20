package com.mulesoft.connectors.inference.internal.connection.types.cohere;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.helpers.payload.CohereRequestPayloadHelper;
import com.mulesoft.connectors.inference.internal.helpers.response.CohereHttpResponseHelper;
import com.mulesoft.connectors.inference.internal.helpers.response.mapper.CohereResponseMapper;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CohereTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat";
  public static final String COHERE_URL = "https://api.cohere.com/v2";

  private CohereResponseMapper responseMapper;
  private CohereRequestPayloadHelper requestPayloadHelper;
  private CohereHttpResponseHelper httpResponseHelper;

  public CohereTextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, ParametersDTO parametersDTO,
                                        Map<String, String> mcpSseServers) {
    super(httpClient, objectMapper, parametersDTO, mcpSseServers, fetchApiURL());
  }

  @Override
  public CohereResponseMapper getResponseMapper() {
    if (responseMapper == null)
      responseMapper = new CohereResponseMapper(this.getObjectMapper());
    return responseMapper;
  }

  @Override
  public CohereRequestPayloadHelper getRequestPayloadHelper() {
    if (requestPayloadHelper == null)
      requestPayloadHelper = new CohereRequestPayloadHelper(getObjectMapper());
    return requestPayloadHelper;
  }

  @Override
  public CohereHttpResponseHelper getResponseHelper() {
    if (httpResponseHelper == null)
      httpResponseHelper = new CohereHttpResponseHelper(getObjectMapper());
    return httpResponseHelper;
  }

  private static String fetchApiURL() {
    return COHERE_URL + URI_CHAT_COMPLETIONS;
  }
}
