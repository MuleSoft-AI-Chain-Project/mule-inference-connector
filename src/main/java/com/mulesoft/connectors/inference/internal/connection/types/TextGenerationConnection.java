package com.mulesoft.connectors.inference.internal.connection.types;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.service.TextGenerationService;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class TextGenerationConnection extends BaseConnection {

  private final Number maxTokens;
  private final Number temperature;
  private final Number topP;
  private TextGenerationService textGenerationService;

  protected TextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, ParametersDTO parametersDTO,
                                     String apiURL) {
    super(httpClient, objectMapper, parametersDTO.modelName(), parametersDTO.apiKey(), parametersDTO.customHeaders(),
          parametersDTO.timeout(), apiURL);
    this.maxTokens = parametersDTO.maxTokens();
    this.temperature = parametersDTO.temperature();
    this.topP = parametersDTO.topP();

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

  public TextGenerationService getTextGenerationService() {
    if (textGenerationService == null)
      textGenerationService = new TextGenerationService(this.getRequestPayloadHelper(), this.getHttpRequestHelper(),
                                                        this.getResponseHelper(), this.getResponseMapper(),
                                                        this.getObjectMapper());
    return textGenerationService;
  }
}
