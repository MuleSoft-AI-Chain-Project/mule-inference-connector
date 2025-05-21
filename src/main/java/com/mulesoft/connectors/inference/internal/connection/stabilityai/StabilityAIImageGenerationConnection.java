package com.mulesoft.connectors.inference.internal.connection.stabilityai;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.ImageGenerationConnection;
import com.mulesoft.connectors.inference.internal.helpers.payload.StabilityAIRequestPayloadHelper;
import com.mulesoft.connectors.inference.internal.helpers.request.StabilityAIHttpRequestHandler;
import com.mulesoft.connectors.inference.internal.helpers.response.HuggingFaceHttpResponseHandler;
import com.mulesoft.connectors.inference.internal.helpers.response.StabilityAIHttpResponseHandler;
import org.mule.runtime.http.api.client.HttpClient;

public class StabilityAIImageGenerationConnection extends ImageGenerationConnection {

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

  @Override
  protected StabilityAIHttpRequestHandler getHttpRequestHandler() {
    return new StabilityAIHttpRequestHandler(this.getObjectMapper());
  }

  @Override
  public StabilityAIHttpResponseHandler getResponseHandler() {
    return new StabilityAIHttpResponseHandler(this.getObjectMapper());
  }
}
