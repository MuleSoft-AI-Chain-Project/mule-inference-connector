package com.mulesoft.connectors.inference.internal.connection.types.xai;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.ImageGenerationConnection;

import com.fasterxml.jackson.databind.ObjectMapper;

public class XAIImageGenerationConnection extends ImageGenerationConnection {

  public XAIImageGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                                      int timeout, String apiURL) {
    super(httpClient, objectMapper, modelName, apiKey, timeout, apiURL);
  }

}
