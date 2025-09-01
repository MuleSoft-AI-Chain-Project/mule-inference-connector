package com.mulesoft.connectors.inference.internal.connection.types.gemini;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.VisionModelConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.helpers.payload.GeminiRequestPayloadHelper;
import com.mulesoft.connectors.inference.internal.helpers.response.GeminiHttpResponseHelper;
import com.mulesoft.connectors.inference.internal.helpers.response.mapper.GeminiResponseMapper;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GeminiVisionConnection extends VisionModelConnection {

  private static final String URI_CHAT_COMPLETIONS = "generateContent";
  public static final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/{model_id}:";

  private GeminiRequestPayloadHelper requestPayloadHelper;
  private GeminiResponseMapper responseMapper;
  private GeminiHttpResponseHelper httpResponseHelper;

  public GeminiVisionConnection(HttpClient httpClient, ObjectMapper objectMapper,
                                ParametersDTO parametersDTO) {
    super(httpClient, objectMapper, parametersDTO, fetchApiURL(parametersDTO.modelName()));
  }

  @Override
  public GeminiRequestPayloadHelper getRequestPayloadHelper() {
    if (requestPayloadHelper == null)
      requestPayloadHelper = new GeminiRequestPayloadHelper(getObjectMapper());
    return requestPayloadHelper;
  }

  @Override
  public GeminiResponseMapper getResponseMapper() {
    if (responseMapper == null)
      responseMapper = new GeminiResponseMapper(this.getObjectMapper());
    return responseMapper;
  }

  @Override
  public GeminiHttpResponseHelper getResponseHelper() {
    if (httpResponseHelper == null)
      httpResponseHelper = new GeminiHttpResponseHelper(getObjectMapper());
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
    String geminiUrlStr = GEMINI_URL + URI_CHAT_COMPLETIONS;
    geminiUrlStr = geminiUrlStr
        .replace("{model_id}", modelName);
    return geminiUrlStr;
  }

}
