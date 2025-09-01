package com.mulesoft.connectors.inference.internal.connection.types;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.api.request.RequestHeader;
import com.mulesoft.connectors.inference.internal.service.ModerationService;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ModerationConnection extends BaseConnection {

  private ModerationService moderationService;

  public ModerationConnection(HttpClient httpClient, ObjectMapper objectMapper,
                              String apiKey, List<RequestHeader> customHeaders, String modelName, int timeout,
                              String apiURL) {
    super(httpClient, objectMapper, modelName, apiKey, customHeaders, timeout, apiURL);
    this.setBaseService(getModerationService());
  }

  public ModerationService getModerationService() {
    if (moderationService == null)
      moderationService = new ModerationService(this.getRequestPayloadHelper(), this.getHttpRequestHelper(),
                                                this.getResponseHelper(), this.getObjectMapper());
    return moderationService;
  }
}
