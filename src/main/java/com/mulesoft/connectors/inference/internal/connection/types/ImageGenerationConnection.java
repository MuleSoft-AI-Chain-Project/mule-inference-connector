package com.mulesoft.connectors.inference.internal.connection.types;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.api.request.RequestHeader;
import com.mulesoft.connectors.inference.internal.service.ImageGenerationService;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ImageGenerationConnection extends BaseConnection {

  private ImageGenerationService imageGenerationService;

  public ImageGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                                   List<RequestHeader> customHeaders, int timeout, String apiURL) {
    super(httpClient, objectMapper, modelName, apiKey, customHeaders, timeout, apiURL);
    this.setBaseService(getImageGenerationService());
  }

  public ImageGenerationService getImageGenerationService() {
    if (imageGenerationService == null)
      imageGenerationService = new ImageGenerationService(this.getRequestPayloadHelper(), this.getHttpRequestHelper(),
                                                          this.getResponseHelper(), this.getObjectMapper());
    return imageGenerationService;
  }
}
