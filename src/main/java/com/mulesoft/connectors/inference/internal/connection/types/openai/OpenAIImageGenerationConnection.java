package com.mulesoft.connectors.inference.internal.connection.types.openai;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.api.request.RequestHeader;
import com.mulesoft.connectors.inference.internal.connection.types.ImageGenerationConnection;
import com.mulesoft.connectors.inference.internal.helpers.payload.OpenAIRequestPayloadHelper;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class OpenAIImageGenerationConnection extends ImageGenerationConnection {

  private OpenAIRequestPayloadHelper requestPayloadHelper;

  public OpenAIImageGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName,
                                         String apiKey, List<RequestHeader> customHeaders,
                                         int timeout, String apiURL) {
    super(httpClient, objectMapper, modelName, apiKey, customHeaders, timeout, apiURL);
  }

  @Override
  public OpenAIRequestPayloadHelper getRequestPayloadHelper() {
    if (requestPayloadHelper == null)
      requestPayloadHelper = new OpenAIRequestPayloadHelper(this.getObjectMapper());
    return requestPayloadHelper;
  }
}
