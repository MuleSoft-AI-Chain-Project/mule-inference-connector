package com.mulesoft.connectors.internal.connection;

import org.mule.runtime.http.api.client.HttpClient;

import java.net.URL;
import java.util.Map;

public abstract class TextGenerationConnection extends BaseConnection{

  private final Number maxTokens;
  private final Number temperature;
  private final Number topP;
  private final Map<String, String> mcpSseServers;

  protected TextGenerationConnection(HttpClient httpClient, String apiKey, String modelName, Number maxTokens, Number temperature, Number topP, int timeout, Map<String, String> mcpSseServers, String apiURL, String inferenceType) {
      super(httpClient,modelName,apiKey,timeout,apiURL,inferenceType);

    this.maxTokens = maxTokens;
    this.temperature = temperature;
    this.topP = topP;
    this.mcpSseServers = mcpSseServers;
  }

  public abstract URL getConnectionURL();
  public abstract Map<String,String> getQueryParams();
  public abstract Map<String,String> getAdditionalHeaders();


  public Number getMaxTokens() {
    return maxTokens;
  }

  public Number getTemperature() {
    return temperature;
  }

  public Number getTopP() {
    return topP;
  }

  public Map<String, String> getMcpSseServers() {
    return mcpSseServers;
  }
}
