package com.mulesoft.connectors.internal.connection;

import org.mule.runtime.http.api.client.HttpClient;

public class BaseConnection {

  private final HttpClient httpClient;
  private final String apiKey;
  private final String modelName;
  private final int timeout;
  private final String apiURL;
  private final String inferenceType; // temporary, to be removed after later refactoring and replaced by individual handlers

  public BaseConnection(HttpClient httpClient, String modelName, String apiKey,
                        int timeout, String apiURL, String inferenceType) {
    this.httpClient = httpClient;
    this.apiKey = apiKey;
    this.modelName = modelName;
    this.timeout = timeout;
    this.apiURL = apiURL;
    this.inferenceType = inferenceType;
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
