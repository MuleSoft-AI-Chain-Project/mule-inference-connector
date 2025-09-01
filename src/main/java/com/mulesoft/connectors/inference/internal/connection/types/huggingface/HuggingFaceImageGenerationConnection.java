package com.mulesoft.connectors.inference.internal.connection.types.huggingface;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.api.request.RequestHeader;
import com.mulesoft.connectors.inference.internal.connection.types.ImageGenerationConnection;
import com.mulesoft.connectors.inference.internal.helpers.payload.HuggingFaceRequestPayloadHelper;
import com.mulesoft.connectors.inference.internal.helpers.response.HuggingFaceHttpResponseHelper;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HuggingFaceImageGenerationConnection extends ImageGenerationConnection {

  private HuggingFaceRequestPayloadHelper requestPayloadHelper;

  public HuggingFaceImageGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                                              List<RequestHeader> customHeaders, int timeout, String apiURL) {
    super(httpClient, objectMapper, modelName, apiKey, customHeaders, timeout, apiURL);
  }

  @Override
  public HuggingFaceRequestPayloadHelper getRequestPayloadHelper() {
    if (requestPayloadHelper == null)
      requestPayloadHelper = new HuggingFaceRequestPayloadHelper(this.getObjectMapper());
    return requestPayloadHelper;
  }

  @Override
  public HuggingFaceHttpResponseHelper getResponseHelper() {
    return new HuggingFaceHttpResponseHelper(this.getObjectMapper());
  }
}
