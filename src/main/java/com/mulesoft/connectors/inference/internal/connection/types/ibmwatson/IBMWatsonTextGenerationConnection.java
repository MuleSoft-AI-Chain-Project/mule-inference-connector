package com.mulesoft.connectors.inference.internal.connection.types.ibmwatson;

import static com.mulesoft.connectors.inference.internal.helpers.payload.IBMWatsonRequestPayloadHelper.executeTokenRequest;

import org.mule.runtime.extension.api.exception.ModuleException;
import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.error.InferenceErrorType;
import com.mulesoft.connectors.inference.internal.helpers.payload.IBMWatsonRequestPayloadHelper;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

public class IBMWatsonTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat?version={api-version}";
  public static final String IBM_WATSON_URL = "https://us-south.ml.cloud.ibm.com/ml/v1/text";
  public static final String IBM_WATSON_TOKEN_URL = "https://iam.cloud.ibm.com/identity/token";
  private final String ibmWatsonApiVersion;

  private IBMWatsonRequestPayloadHelper requestPayloadHelper;

  public IBMWatsonTextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper,
                                           ParametersDTO parametersDTO, String ibmWatsonApiVersion,
                                           Map<String, String> mcpSseServers) {
    super(httpClient, objectMapper, parametersDTO, mcpSseServers,
          fetchApiURL(ibmWatsonApiVersion));
    this.ibmWatsonApiVersion = ibmWatsonApiVersion;
  }

  @Override
  public Map<String, String> getAdditionalHeaders() {
    // The logic for obtaining the access token should ideally be moved to a separate token request handler or use runtime
    // injection in the future.
    Map<String, String> params = new HashMap<>();
    params.put("grant_type", "urn:ibm:params:oauth:grant-type:apikey");
    params.put("apikey", this.getApiKey()); // Use connection.getApiKey() instead of hardcoded
    String response = null;
    try {
      URL tokenUrl = new URL(IBM_WATSON_TOKEN_URL);
      response = executeTokenRequest(tokenUrl, this, params);
    } catch (IOException | TimeoutException e) {
      throw new ModuleException("Error fetching the token for ibm watson.", InferenceErrorType.INVALID_CONNECTION, e);
    }
    JSONObject jsonResponse = new JSONObject(response);
    String accessToken = jsonResponse.getString("access_token");
    return Map.of("Authorization", "Bearer " + accessToken);
  }

  @Override
  public IBMWatsonRequestPayloadHelper getRequestPayloadHelper() {
    if (requestPayloadHelper == null)
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
