package com.mulesoft.connectors.inference.internal.connection.types.vertexai;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.VisionModelConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.helpers.payload.VertexAIRequestPayloadHelper;
import com.mulesoft.connectors.inference.internal.helpers.response.VertexAIHttpResponseHelper;
import com.mulesoft.connectors.inference.internal.helpers.response.mapper.VertexAIResponseMapper;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class VertexAIExpressVisionConnection extends VisionModelConnection {

  private static final String URI_CHAT_COMPLETIONS = "generateContent";
  public static final String VERTEX_AI_EXPRESS_URL = "https://aiplatform.googleapis.com/v1/publishers/google/models/{model_id}:";

  private VertexAIRequestPayloadHelper requestPayloadHelper;
  private VertexAIResponseMapper responseMapper;
  private VertexAIHttpResponseHelper httpResponseHelper;

  public VertexAIExpressVisionConnection(HttpClient httpClient, ObjectMapper objectMapper,
                                         ParametersDTO parametersDTO) {
    super(httpClient, objectMapper, parametersDTO, fetchApiURL(parametersDTO.modelName()));
  }

  @Override
  public VertexAIRequestPayloadHelper getRequestPayloadHelper() {
    if (requestPayloadHelper == null)
      requestPayloadHelper = new VertexAIRequestPayloadHelper(getObjectMapper());
    return requestPayloadHelper;
  }

  @Override
  public VertexAIResponseMapper getResponseMapper() {
    if (responseMapper == null)
      responseMapper = new VertexAIResponseMapper(this.getObjectMapper());
    return responseMapper;
  }

  @Override
  public VertexAIHttpResponseHelper getResponseHelper() {
    if (httpResponseHelper == null)
      httpResponseHelper = new VertexAIHttpResponseHelper(getObjectMapper());
    return httpResponseHelper;
  }

  @Override
  public Map<String, String> getQueryParams() {
    return Map.of("key", this.getApiKey());
  }

  @Override
  public Map<String, String> getAdditionalHeaders() {
    return getCustomHeadersMap();
  }

  private static String fetchApiURL(String modelName) {
    String vertexAIExpressUrlStr = VERTEX_AI_EXPRESS_URL + URI_CHAT_COMPLETIONS;
    vertexAIExpressUrlStr = vertexAIExpressUrlStr
        .replace("{model_id}", modelName);
    return vertexAIExpressUrlStr;
  }
}
