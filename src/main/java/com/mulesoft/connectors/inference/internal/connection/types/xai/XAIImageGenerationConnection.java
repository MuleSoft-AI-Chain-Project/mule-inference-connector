package com.mulesoft.connectors.inference.internal.connection.types.xai;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.ImageGenerationConnection;
import com.mulesoft.connectors.inference.internal.helpers.payload.XAIRequestPayloadHelper;

import com.fasterxml.jackson.databind.ObjectMapper;

public class XAIImageGenerationConnection extends ImageGenerationConnection {

  private XAIRequestPayloadHelper requestPayloadHelper;

  public XAIImageGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                                      int timeout, String apiURL, String inferenceType) {
    super(httpClient, objectMapper, modelName, apiKey, timeout, apiURL, inferenceType);
  }

  @Override
  public XAIRequestPayloadHelper getRequestPayloadHelper() {
    if (requestPayloadHelper == null)
      requestPayloadHelper = new XAIRequestPayloadHelper(this.getObjectMapper());
    return requestPayloadHelper;
  }
}
