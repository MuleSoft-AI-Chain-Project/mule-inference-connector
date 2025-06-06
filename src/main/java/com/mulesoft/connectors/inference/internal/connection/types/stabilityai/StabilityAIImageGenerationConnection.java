package com.mulesoft.connectors.inference.internal.connection.types.stabilityai;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.ImageGenerationConnection;
import com.mulesoft.connectors.inference.internal.helpers.payload.StabilityAIRequestPayloadHelper;
import com.mulesoft.connectors.inference.internal.helpers.request.StabilityAIHttpRequestHelper;
import com.mulesoft.connectors.inference.internal.helpers.response.StabilityAIHttpResponseHelper;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StabilityAIImageGenerationConnection extends ImageGenerationConnection {

  private StabilityAIRequestPayloadHelper requestPayloadHelper;

  public StabilityAIImageGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                                              int timeout, String apiURL, String inferenceType) {
    super(httpClient, objectMapper, modelName, apiKey, timeout, apiURL, inferenceType);
  }

  @Override
  public StabilityAIRequestPayloadHelper getRequestPayloadHelper() {
    if (requestPayloadHelper == null)
      requestPayloadHelper = new StabilityAIRequestPayloadHelper(this.getObjectMapper());
    return requestPayloadHelper;
  }

  @Override
  protected StabilityAIHttpRequestHelper getHttpRequestHandler() {
    return new StabilityAIHttpRequestHelper(this.getHttpClient(), this.getObjectMapper());
  }

  @Override
  public StabilityAIHttpResponseHelper getResponseHandler() {
    return new StabilityAIHttpResponseHelper(this.getObjectMapper());
  }
}
