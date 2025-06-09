package com.mulesoft.connectors.inference.internal.connection.types.anthropic;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.VisionModelConnection;
import com.mulesoft.connectors.inference.internal.helpers.payload.AnthropicRequestPayloadHelper;
import com.mulesoft.connectors.inference.internal.helpers.response.AnthropicHttpResponseHelper;
import com.mulesoft.connectors.inference.internal.helpers.response.mapper.AnthropicResponseMapper;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AnthropicVisionConnection extends VisionModelConnection {

  private static final String URI_CHAT_COMPLETIONS = "/messages";
  public static final String ANTHROPIC_URL = "https://api.anthropic.com/v1";

  private AnthropicRequestPayloadHelper requestPayloadHelper;
  private AnthropicResponseMapper responseMapper;
  private AnthropicHttpResponseHelper httpResponseHelper;

  public AnthropicVisionConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                                   Number temperature, Number topP,
                                   Number maxTokens, int timeout) {
    super(httpClient, objectMapper, apiKey, modelName, maxTokens, temperature, topP, timeout, fetchApiURL(), "ANTHROPIC");
  }

  @Override
  public AnthropicRequestPayloadHelper getRequestPayloadHelper() {
    if (requestPayloadHelper == null)
      requestPayloadHelper = new AnthropicRequestPayloadHelper(getObjectMapper());
    return requestPayloadHelper;
  }

  @Override
  public AnthropicResponseMapper getResponseMapper() {
    if (responseMapper == null)
      responseMapper = new AnthropicResponseMapper(this.getObjectMapper());
    return responseMapper;
  }

  @Override
  public AnthropicHttpResponseHelper getResponseHelper() {
    if (httpResponseHelper == null)
      httpResponseHelper = new AnthropicHttpResponseHelper(getObjectMapper());
    return httpResponseHelper;
  }

  @Override
  public Map<String, String> getAdditionalHeaders() {
    return Map.of("x-api-key", this.getApiKey(), "anthropic-version", "2023-06-01");
  }

  private static String fetchApiURL() {
    return ANTHROPIC_URL + URI_CHAT_COMPLETIONS;
  }
}
