package com.mulesoft.connectors.inference.internal.connection.huggingface;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.ImageGenerationConnection;
import com.mulesoft.connectors.inference.internal.helpers.payload.HuggingFaceRequestPayloadHelper;
import com.mulesoft.connectors.inference.internal.helpers.response.HuggingFaceHttpResponseHandler;
import org.mule.runtime.http.api.client.HttpClient;

public class HuggingFaceImageGenerationConnection extends ImageGenerationConnection {

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

  @Override
  public HuggingFaceHttpResponseHandler getResponseHandler() {
    return new HuggingFaceHttpResponseHandler(this.getObjectMapper());
  }
}
