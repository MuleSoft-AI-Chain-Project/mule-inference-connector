package com.mulesoft.connectors.inference.internal.connection.types.portkey;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.VisionModelConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PortkeyVisionConnection extends VisionModelConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String PORTKEY_URL = "https://api.portkey.ai/v1";
  private final String virtualKey;

  public PortkeyVisionConnection(HttpClient httpClient, ObjectMapper objectMapper,
                                 ParametersDTO parametersDTO, String virtualKey) {
    super(httpClient, objectMapper, parametersDTO, fetchApiURL());
    this.virtualKey = virtualKey;
  }

  @Override
  public Map<String, String> getAdditionalHeaders() {
    Map<String, String> headers = new HashMap<>();

    headers.put("x-portkey-api-key", this.getApiKey());
    headers.put("x-portkey-virtual-key", this.virtualKey);
    headers.putAll(getCustomHeadersMap());
    return headers;
  }

  private static String fetchApiURL() {
    return PORTKEY_URL + URI_CHAT_COMPLETIONS;
  }
}
