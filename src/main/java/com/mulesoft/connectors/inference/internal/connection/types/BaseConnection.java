package com.mulesoft.connectors.inference.internal.connection.types;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.api.request.RequestHeader;
import com.mulesoft.connectors.inference.internal.helpers.payload.RequestPayloadHelper;
import com.mulesoft.connectors.inference.internal.helpers.request.HttpRequestHelper;
import com.mulesoft.connectors.inference.internal.helpers.response.HttpResponseHelper;
import com.mulesoft.connectors.inference.internal.helpers.response.mapper.DefaultResponseMapper;
import com.mulesoft.connectors.inference.internal.service.BaseService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseConnection {

  private final HttpClient httpClient;
  private final String apiKey;
  private final String modelName;
  private final List<RequestHeader> customHeaders;
  private final int timeout;
  private final String apiURL;
  private final ObjectMapper objectMapper;
  private RequestPayloadHelper requestPayloadHelper;

  private BaseService baseService;

  public BaseConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                        List<RequestHeader> customHeaders,
                        int timeout, String apiURL) {
    this.httpClient = httpClient;
    this.objectMapper = objectMapper;
    this.apiKey = apiKey;
    this.modelName = modelName;
    this.customHeaders = customHeaders;
    this.timeout = timeout;
    this.apiURL = apiURL;
  }

  public void setBaseService(BaseService baseService) {
    this.baseService = baseService;
  }

  public BaseService getService() {
    return baseService;
  }

  public RequestPayloadHelper getRequestPayloadHelper() {
    if (requestPayloadHelper == null)
      requestPayloadHelper = new RequestPayloadHelper(objectMapper);
    return requestPayloadHelper;
  }

  protected HttpRequestHelper getHttpRequestHelper() {
    return new HttpRequestHelper(this.getHttpClient(), this.getObjectMapper());
  }

  protected HttpResponseHelper getResponseHelper() {
    return new HttpResponseHelper(this.getObjectMapper());
  }

  protected DefaultResponseMapper getResponseMapper() {
    return new DefaultResponseMapper(this.getObjectMapper());
  }

  public Map<String, String> getAdditionalHeaders() {

    Map<String, String> headers = new HashMap<>();

    headers.put("Authorization", "Bearer " + this.getApiKey());
    headers.putAll(getCustomHeadersMap());
    return headers;
  }

  public Map<String, String> getQueryParams() {
    return Collections.emptyMap();
  }

  public String getApiKey() {
    return apiKey;
  }

  public String getModelName() {
    return modelName;
  }

  public HttpClient getHttpClient() {
    return httpClient;
  }

  public ObjectMapper getObjectMapper() {
    return objectMapper;
  }

  public int getTimeout() {
    return timeout;
  }

  public String getApiURL() {
    return apiURL;
  }

  protected Map<String, String> getCustomHeadersMap() {
    return Optional.ofNullable(customHeaders)
        .orElse(Collections.emptyList())
        .stream()
        .filter(header -> header.getKey() != null && header.getValue() != null)
        .collect(Collectors.toMap(RequestHeader::getKey, RequestHeader::getValue,
                                  (existing, replacement) -> replacement));
  }
}
