package com.mulesoft.connectors.inference.internal.connection.types;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.service.ImageGenerationService;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ImageGenerationConnection extends BaseConnection {

  private ImageGenerationService imageGenerationService;

  public ImageGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                                   int timeout, String apiURL, String inferenceType) {
    super(httpClient, objectMapper, modelName, apiKey, timeout, apiURL, inferenceType);
    this.setBaseService(getImageGenerationService());
  }

  public ImageGenerationService getImageGenerationService() {
    if (imageGenerationService == null)
      imageGenerationService = new ImageGenerationService(this.getRequestPayloadHelper(), this.getHttpRequestHandler(),
                                                          this.getResponseHandler(), this.getObjectMapper());
    return imageGenerationService;
  }
}
