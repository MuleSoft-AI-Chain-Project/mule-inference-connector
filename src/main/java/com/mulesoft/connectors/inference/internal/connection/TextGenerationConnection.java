package com.mulesoft.connectors.inference.internal.connection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.service.TextGenerationService;
import org.mule.runtime.http.api.client.HttpClient;

import java.util.Map;

public abstract class TextGenerationConnection extends BaseConnection{

  private final Number maxTokens;
  private final Number temperature;
  private final Number topP;
  private final Map<String, String> mcpSseServers;
  private TextGenerationService textGenerationService;

  protected TextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper,
                                     String apiKey, String modelName, Number maxTokens, Number temperature,
                                     Number topP, int timeout, Map<String, String> mcpSseServers,
                                     String apiURL, String inferenceType) {
    super(httpClient,objectMapper,modelName,apiKey,timeout,apiURL,inferenceType);
    this.maxTokens = maxTokens;
    this.temperature = temperature;
    this.topP = topP;
    this.mcpSseServers = mcpSseServers;

    this.setBaseService(getTextGenerationService());
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

  public Map<String, String> getMcpSseServers() {
    return mcpSseServers;
  }

  public TextGenerationService getTextGenerationService() {
    if(textGenerationService==null)
      textGenerationService = new TextGenerationService(this.getRequestPayloadHelper(),this.getHttpRequestHandler(),
              this.getResponseHandler(), this.getMcpHelper(), this.getObjectMapper());
    return textGenerationService;
  }
}
