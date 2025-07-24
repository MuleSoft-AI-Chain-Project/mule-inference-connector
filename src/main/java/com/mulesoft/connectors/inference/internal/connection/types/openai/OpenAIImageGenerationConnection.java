package com.mulesoft.connectors.inference.internal.connection.types.openai;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.ImageGenerationConnection;
import com.mulesoft.connectors.inference.internal.helpers.payload.OpenAIRequestPayloadHelper;

import com.fasterxml.jackson.databind.ObjectMapper;

public class OpenAIImageGenerationConnection extends ImageGenerationConnection {

  private OpenAIRequestPayloadHelper requestPayloadHelper;

  public OpenAIImageGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                                         int timeout, String apiURL) {
    super(httpClient, objectMapper, modelName, apiKey, timeout, apiURL);
  }

  @Override
  public OpenAIRequestPayloadHelper getRequestPayloadHelper() {
    if (requestPayloadHelper == null)
      requestPayloadHelper = new OpenAIRequestPayloadHelper(this.getObjectMapper());
    return requestPayloadHelper;
  }
}
