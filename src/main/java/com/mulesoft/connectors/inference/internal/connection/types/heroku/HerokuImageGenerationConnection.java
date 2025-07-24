package com.mulesoft.connectors.inference.internal.connection.types.heroku;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.ImageGenerationConnection;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HerokuImageGenerationConnection extends ImageGenerationConnection {

  public HerokuImageGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                                         int timeout, String apiURL) {
    super(httpClient, objectMapper, modelName, apiKey, timeout, apiURL);
  }
}
