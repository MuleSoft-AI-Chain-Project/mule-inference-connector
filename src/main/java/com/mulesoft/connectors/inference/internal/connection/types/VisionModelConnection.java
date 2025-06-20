package com.mulesoft.connectors.inference.internal.connection.types;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.service.VisionModelService;

import com.fasterxml.jackson.databind.ObjectMapper;

public class VisionModelConnection extends BaseConnection {

  private final Number maxTokens;
  private final Number temperature;
  private final Number topP;

  private VisionModelService visionModelService;

  protected VisionModelConnection(HttpClient httpClient, ObjectMapper objectMapper, ParametersDTO parametersDTO, String apiUrl) {
    super(httpClient, objectMapper, parametersDTO.modelName(), parametersDTO.apiKey(),
          parametersDTO.timeout(), apiUrl);
    this.maxTokens = parametersDTO.maxTokens();
    this.temperature = parametersDTO.temperature();
    this.topP = parametersDTO.topP();
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
      visionModelService = new VisionModelService(this.getRequestPayloadHelper(), this.getHttpRequestHelper(),
                                                  this.getResponseHelper(), this.getResponseMapper(), this.getObjectMapper());
    return visionModelService;
  }
}
