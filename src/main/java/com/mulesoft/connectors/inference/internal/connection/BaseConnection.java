package com.mulesoft.connectors.inference.internal.connection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.helpers.McpHelper;
import com.mulesoft.connectors.inference.internal.helpers.payload.RequestPayloadHelper;
import com.mulesoft.connectors.inference.internal.helpers.request.HttpRequestHandler;
import com.mulesoft.connectors.inference.internal.helpers.response.HttpResponseHandler;
import com.mulesoft.connectors.inference.internal.service.BaseService;
import org.mule.runtime.http.api.client.HttpClient;

import java.util.Collections;
import java.util.Map;

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

  public RequestPayloadHelper getRequestPayloadHelper(){
    if(requestPayloadHelper == null)
      requestPayloadHelper = new RequestPayloadHelper(objectMapper);
    return requestPayloadHelper;
  }

  protected McpHelper getMcpHelper(){
    if(mcpHelper == null)
      mcpHelper = new McpHelper(this.getObjectMapper());
    return mcpHelper;
  }

  protected HttpRequestHandler getHttpRequestHandler() {
    return new HttpRequestHandler(this.getObjectMapper());
  }

  protected HttpResponseHandler getResponseHandler() {
    return new HttpResponseHandler(this.getObjectMapper());
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
