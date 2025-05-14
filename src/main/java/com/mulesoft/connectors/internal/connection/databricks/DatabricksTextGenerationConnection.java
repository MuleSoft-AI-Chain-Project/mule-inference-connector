package com.mulesoft.connectors.internal.connection.databricks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import org.mule.runtime.http.api.client.HttpClient;

import java.util.Map;

public class DatabricksTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/serving-endpoints/{model_name}/invocations";

  public DatabricksTextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String databricksModelName, String databricksModelURL, String apiKey,
                                            Number temperature, Number topP,
                                            Number maxTokens, Map<String, String> mcpSseServers, int timeout) {
    super(httpClient, objectMapper, apiKey, databricksModelName, maxTokens, temperature, topP, timeout, mcpSseServers,
            fetchApiURL(databricksModelURL,databricksModelName), "DATABRICKS");
  }

  @Override
  public Map<String, String> getQueryParams() {
    return Map.of();
  }

  @Override
  public Map<String, String> getAdditionalHeaders() {
    return Map.of("Authorization", "Bearer " + this.getApiKey());
  }

  private static String fetchApiURL(String databricksModelURL, String databricksModelName) {
      String dBricksUrlStr = databricksModelURL + URI_CHAT_COMPLETIONS;
      dBricksUrlStr = dBricksUrlStr
              .replace("{model_name}", databricksModelName);
    return dBricksUrlStr;
  }
} 