package com.mulesoft.connectors.inference.internal.connection.types.xai;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.api.request.RequestHeader;
import com.mulesoft.connectors.inference.internal.connection.types.ImageGenerationConnection;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class XAIImageGenerationConnection extends ImageGenerationConnection {

  public XAIImageGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                                      List<RequestHeader> customHeaders, int timeout, String apiURL) {
    super(httpClient, objectMapper, modelName, apiKey, customHeaders, timeout, apiURL);
  }

}
