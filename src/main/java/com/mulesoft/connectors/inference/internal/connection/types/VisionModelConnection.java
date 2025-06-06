package com.mulesoft.connectors.inference.internal.connection.types;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.service.VisionModelService;

import com.fasterxml.jackson.databind.ObjectMapper;

public class VisionModelConnection extends BaseConnection {

  private final Number maxTokens;
  private final Number temperature;
  private final Number topP;

  private VisionModelService visionModelService;

  protected VisionModelConnection(HttpClient httpClient, ObjectMapper objectMapper,
                                  String apiKey, String modelName, Number maxTokens, Number temperature,
                                  Number topP, int timeout,
                                  String apiURL, String inferenceType) {
    super(httpClient, objectMapper, modelName, apiKey, timeout, apiURL, inferenceType);
    this.maxTokens = maxTokens;
    this.temperature = temperature;
    this.topP = topP;
    this.setBaseService(getVisionModelService());
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

  public VisionModelService getVisionModelService() {
    if (visionModelService == null)
      visionModelService = new VisionModelService(this.getRequestPayloadHelper(), this.getHttpRequestHandler(),
                                                  this.getResponseHandler(), this.getObjectMapper());
    return visionModelService;
  }
}
