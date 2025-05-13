package com.mulesoft.connectors.internal.connection.databricks;

import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.internal.constants.InferenceConstants;
import org.mule.runtime.http.api.client.HttpClient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class DatabricksTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/serving-endpoints/{model_name}/invocations";

  public DatabricksTextGenerationConnection(HttpClient httpClient, String databricksModelName, String databricksModelURL, String apiKey,
                                            Number temperature, Number topP,
                                            Number maxTokens, Map<String, String> mcpSseServers, int timeout)
          throws MalformedURLException {
    super(httpClient, apiKey, databricksModelName, maxTokens, temperature, topP, timeout, mcpSseServers,
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
      String dBricksUrlStr = databricksModelURL + InferenceConstants.CHAT_COMPLETIONS_DATABRICKS;
      dBricksUrlStr = dBricksUrlStr
              .replace("{model_name}", databricksModelName);
    return dBricksUrlStr;
  }
} 