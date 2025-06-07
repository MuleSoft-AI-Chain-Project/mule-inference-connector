package com.mulesoft.connectors.inference.internal.connection.types;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.helpers.McpHelper;
import com.mulesoft.connectors.inference.internal.helpers.payload.RequestPayloadHelper;
import com.mulesoft.connectors.inference.internal.helpers.request.HttpRequestHelper;
import com.mulesoft.connectors.inference.internal.helpers.response.HttpResponseHelper;
import com.mulesoft.connectors.inference.internal.helpers.response.mapper.DefaultResponseMapper;
import com.mulesoft.connectors.inference.internal.service.BaseService;

import java.util.Collections;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseConnection {

  private final HttpClient httpClient;
  private final String apiKey;
  private final String modelName;
  private final int timeout;
  private final String apiURL;
  private final String inferenceType; // temporary, to be removed after later refactoring and replaced by individual handlers
  private final ObjectMapper objectMapper;
  private RequestPayloadHelper requestPayloadHelper;
  private McpHelper mcpHelper;
  private BaseService baseService;

  public BaseConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                        int timeout, String apiURL, String inferenceType) {
    this.httpClient = httpClient;
    this.objectMapper = objectMapper;
    this.apiKey = apiKey;
    this.modelName = modelName;
    this.timeout = timeout;
    this.apiURL = apiURL;
    this.inferenceType = inferenceType;
  }

  public void setBaseService(BaseService baseService) {
    this.baseService = baseService;
  }

  public BaseService getService() {
    return baseService;
  }

  public RequestPayloadHelper getRequestPayloadHelper() {
    if (requestPayloadHelper == null)
      requestPayloadHelper = new RequestPayloadHelper(objectMapper);
    return requestPayloadHelper;
  }

  protected McpHelper getMcpHelper() {
    if (mcpHelper == null)
      mcpHelper = new McpHelper(this.getObjectMapper());
    return mcpHelper;
  }

  protected HttpRequestHelper getHttpRequestHelper() {
    return new HttpRequestHelper(this.getHttpClient(), this.getObjectMapper());
  }

  protected HttpResponseHelper getResponseHelper() {
    return new HttpResponseHelper(this.getObjectMapper());
  }

  protected DefaultResponseMapper getResponseMapper() {
    return new DefaultResponseMapper(this.getObjectMapper());
  }

  public Map<String, String> getAdditionalHeaders() {
    return Map.of("Authorization", "Bearer " + this.getApiKey());
  }

  public Map<String, String> getQueryParams() {
    return Collections.emptyMap();
  }

  public String getApiKey() {
    return apiKey;
  }

  public String getModelName() {
    return modelName;
  }

  public HttpClient getHttpClient() {
    return httpClient;
  }

  public ObjectMapper getObjectMapper() {
    return objectMapper;
  }

  public int getTimeout() {
    return timeout;
  }

  public String getApiURL() {
    return apiURL;
  }

  public String getInferenceType() {
    return inferenceType;
  }
}
