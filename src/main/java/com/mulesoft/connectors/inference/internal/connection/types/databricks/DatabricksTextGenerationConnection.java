package com.mulesoft.connectors.inference.internal.connection.types.databricks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
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

  private static String fetchApiURL(String databricksModelURL, String databricksModelName) {
      String dBricksUrlStr = databricksModelURL + URI_CHAT_COMPLETIONS;
      dBricksUrlStr = dBricksUrlStr
              .replace("{model_name}", databricksModelName);
    return dBricksUrlStr;
  }
} 