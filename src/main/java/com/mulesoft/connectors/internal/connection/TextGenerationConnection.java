package com.mulesoft.connectors.internal.connection;

import org.mule.runtime.http.api.client.HttpClient;

import java.net.URL;
import java.util.Map;

public abstract class TextGenerationConnection {

  private final HttpClient httpClient;
  private final String apiKey;
  private final String modelName;
  private final Number maxTokens;
  private final Number temperature;
  private final Number topP;
  private final int timeout;
  private final Map<String, String> mcpSseServers;

  protected TextGenerationConnection(HttpClient httpClient, String apiKey, String modelName, Number maxTokens, Number temperature, Number topP, int timeout, Map<String, String> mcpSseServers) {
    this.httpClient = httpClient;
    this.apiKey = apiKey;
    this.modelName = modelName;
    this.maxTokens = maxTokens;
    this.temperature = temperature;
    this.topP = topP;
    this.timeout = timeout;
    this.mcpSseServers = mcpSseServers;
  }

  public abstract String getInferenceType();  //TODO to be removed and replaced by individual handlers instead
  public abstract URL getConnectionURL();
  public abstract Map<String,String> getQueryParams();
  public abstract Map<String,String> getAdditionalHeaders();

  public String getApiKey() {
    return apiKey;
  }

  public String getModelName() {
    return modelName;
  }

  public Number getMaxTokens() {
    return maxTokens;
  }

  public Number getTemperature() {
    return temperature;
  }

  public Number getTopP() {
    return topP;
  }

  public HttpClient getHttpClient() {
    return httpClient;
  }

  public int getTimeout() {
    return timeout;
  }

  public Map<String, String> getMcpSseServers() {
    return mcpSseServers;
  }
}
