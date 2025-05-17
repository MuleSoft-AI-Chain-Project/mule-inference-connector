package com.mulesoft.connectors.inference.internal.connection.huggingface;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.BaseConnection;
import com.mulesoft.connectors.inference.internal.helpers.request.HuggingFaceRequestPayloadHelper;
import org.mule.runtime.http.api.client.HttpClient;

public class HuggingFaceImageGenerationConnection extends BaseConnection {

  private HuggingFaceRequestPayloadHelper requestPayloadHelper;

  public HuggingFaceImageGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                                              int timeout, String apiURL, String inferenceType) {
      super(httpClient, objectMapper, modelName, apiKey, timeout, apiURL, inferenceType);
  }

  @Override
  public HuggingFaceRequestPayloadHelper getRequestPayloadHelper(){
    if(requestPayloadHelper == null)
      requestPayloadHelper = new HuggingFaceRequestPayloadHelper(this.getObjectMapper());
    return requestPayloadHelper;
  }
}
