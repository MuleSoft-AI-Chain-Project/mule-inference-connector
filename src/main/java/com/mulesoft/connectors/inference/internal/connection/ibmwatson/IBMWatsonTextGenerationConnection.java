package com.mulesoft.connectors.inference.internal.connection.ibmwatson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.helpers.payload.IBMWatsonRequestPayloadHelper;
import org.mule.runtime.http.api.client.HttpClient;

import java.util.Map;

public class IBMWatsonTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat?version={api-version}";
  public static final String IBM_WATSON_URL = "https://us-south.ml.cloud.ibm.com/ml/v1/text";
  public static final String IBM_WATSON_Token_URL = "https://iam.cloud.ibm.com/identity/token";
  private final String ibmWatsonApiVersion;

  private IBMWatsonRequestPayloadHelper requestPayloadHelper;

  public IBMWatsonTextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String ibmWatsonApiVersion,
                                           String apiKey, Number temperature, Number topP,
                                           Number maxTokens, Map<String, String> mcpSseServers, int timeout) {
    super(httpClient, objectMapper, apiKey, modelName, maxTokens, temperature, topP, timeout, mcpSseServers, fetchApiURL(ibmWatsonApiVersion), "IBMWATSON");
    this.ibmWatsonApiVersion =ibmWatsonApiVersion;
  }

  @Override
  public Map<String, String> getQueryParams() {
    return Map.of();
  }

  @Override
  public Map<String, String> getAdditionalHeaders() {
    return Map.of("Authorization", "Bearer " + this.getApiKey());
  }

  @Override
  public IBMWatsonRequestPayloadHelper getRequestPayloadHelper(){
    if(requestPayloadHelper == null)
      requestPayloadHelper = new IBMWatsonRequestPayloadHelper(getObjectMapper());
    return requestPayloadHelper;
  }

  public String getIbmWatsonApiVersion() {
    return ibmWatsonApiVersion;
  }

  private static String fetchApiURL(String ibmWatsonApiVersion) {
    String ibmwurlStr = IBM_WATSON_URL + URI_CHAT_COMPLETIONS;
    ibmwurlStr = ibmwurlStr
            .replace("{api-version}", ibmWatsonApiVersion);
    return ibmwurlStr;
  }

} 