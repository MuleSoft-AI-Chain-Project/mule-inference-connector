package com.mulesoft.connectors.inference.internal.connection.stabilityai;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.BaseConnection;
import com.mulesoft.connectors.inference.internal.helpers.request.StabilityAIRequestPayloadHelper;
import org.mule.runtime.http.api.client.HttpClient;

public class StabilityAIImageGenerationConnection extends BaseConnection {

  private StabilityAIRequestPayloadHelper requestPayloadHelper;

  public StabilityAIImageGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                                              int timeout, String apiURL, String inferenceType) {
      super(httpClient, objectMapper, modelName, apiKey, timeout, apiURL, inferenceType);
  }

  @Override
  public StabilityAIRequestPayloadHelper getRequestPayloadHelper(){
    if(requestPayloadHelper == null)
      requestPayloadHelper = new StabilityAIRequestPayloadHelper(this.getObjectMapper());
    return requestPayloadHelper;
  }

}
