package com.mulesoft.connectors.inference.internal.connection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.helpers.request.RequestPayloadHelper;
import org.mule.runtime.http.api.client.HttpClient;

public class BaseConnection {

  private final HttpClient httpClient;
  private final String apiKey;
  private final String modelName;
  private final int timeout;
  private final String apiURL;
  private final String inferenceType; // temporary, to be removed after later refactoring and replaced by individual handlers
  private final ObjectMapper objectMapper;
  private RequestPayloadHelper requestPayloadHelper;

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

  public RequestPayloadHelper getRequestPayloadHelper(){
    if(requestPayloadHelper == null)
      requestPayloadHelper = new RequestPayloadHelper(objectMapper);
    return requestPayloadHelper;
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
