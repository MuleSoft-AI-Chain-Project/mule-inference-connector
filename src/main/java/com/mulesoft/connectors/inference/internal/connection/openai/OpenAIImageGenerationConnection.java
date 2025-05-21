package com.mulesoft.connectors.inference.internal.connection.openai;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.ImageGenerationConnection;
import com.mulesoft.connectors.inference.internal.helpers.payload.OpenAIRequestPayloadHelper;
import org.mule.runtime.http.api.client.HttpClient;

public class OpenAIImageGenerationConnection extends ImageGenerationConnection {

  private OpenAIRequestPayloadHelper requestPayloadHelper;

  public OpenAIImageGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                                         int timeout, String apiURL, String inferenceType) {
      super(httpClient, objectMapper, modelName, apiKey, timeout, apiURL, inferenceType);
  }

  @Override
  public OpenAIRequestPayloadHelper getRequestPayloadHelper(){
    if(requestPayloadHelper == null)
      requestPayloadHelper = new OpenAIRequestPayloadHelper(this.getObjectMapper());
    return requestPayloadHelper;
  }
}
