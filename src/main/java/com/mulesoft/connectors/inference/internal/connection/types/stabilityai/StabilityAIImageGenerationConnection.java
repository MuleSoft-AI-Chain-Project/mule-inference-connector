package com.mulesoft.connectors.inference.internal.connection.types.stabilityai;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.api.request.RequestHeader;
import com.mulesoft.connectors.inference.internal.connection.types.ImageGenerationConnection;
import com.mulesoft.connectors.inference.internal.helpers.payload.StabilityAIRequestPayloadHelper;
import com.mulesoft.connectors.inference.internal.helpers.request.StabilityAIHttpRequestHelper;
import com.mulesoft.connectors.inference.internal.helpers.response.StabilityAIHttpResponseHelper;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StabilityAIImageGenerationConnection extends ImageGenerationConnection {

  private StabilityAIRequestPayloadHelper requestPayloadHelper;

  public StabilityAIImageGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                                              List<RequestHeader> customHeaders, int timeout, String apiURL) {
    super(httpClient, objectMapper, modelName, apiKey, customHeaders, timeout, apiURL);
  }

  @Override
  public StabilityAIRequestPayloadHelper getRequestPayloadHelper() {
    if (requestPayloadHelper == null)
      requestPayloadHelper = new StabilityAIRequestPayloadHelper(this.getObjectMapper());
    return requestPayloadHelper;
  }

  @Override
  protected StabilityAIHttpRequestHelper getHttpRequestHelper() {
    return new StabilityAIHttpRequestHelper(this.getHttpClient(), this.getObjectMapper());
  }

  @Override
  public StabilityAIHttpResponseHelper getResponseHelper() {
    return new StabilityAIHttpResponseHelper(this.getObjectMapper());
  }
}
