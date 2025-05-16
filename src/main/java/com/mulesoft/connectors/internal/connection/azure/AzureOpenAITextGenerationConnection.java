package com.mulesoft.connectors.internal.connection.azure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.internal.helpers.textgeneration.AzureOpenAIRequestPayloadHelper;
import org.mule.runtime.http.api.client.HttpClient;

import java.util.Map;

public class AzureOpenAITextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions?api-version=2024-10-21";
  public static final String AZURE_OPENAI_URL = "https://{resource-name}.openai.azure.com/openai/deployments/{deployment-id}";

  private AzureOpenAIRequestPayloadHelper requestPayloadHelper;

  public AzureOpenAITextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName,
                                             String openaiResourceName, String openaiDeploymentId,
                                             String apiKey,
                                             Number temperature, Number topP,
                                             Number maxTokens, Map<String, String> mcpSseServers, int timeout) {
    super(httpClient, objectMapper, apiKey, modelName, maxTokens, temperature, topP, timeout, mcpSseServers,
            fetchApiURL(openaiResourceName, openaiDeploymentId), "AZURE");
  }

  @Override
  public AzureOpenAIRequestPayloadHelper getRequestPayloadHelper(){
    if(requestPayloadHelper == null)
      requestPayloadHelper = new AzureOpenAIRequestPayloadHelper(getObjectMapper());
    return requestPayloadHelper;
  }

  @Override
  public Map<String, String> getQueryParams() {
    return Map.of();
  }

  @Override
  public Map<String, String> getAdditionalHeaders() {
    return Map.of("Authorization", "Bearer " + this.getApiKey());
  }

  private static String fetchApiURL(String openaiResourceName, String openaiDeploymentId) {

    String urlStr = AZURE_OPENAI_URL + URI_CHAT_COMPLETIONS;
    urlStr = urlStr
            .replace("{resource-name}", openaiResourceName)
            .replace("{deployment-id}", openaiDeploymentId);
    return urlStr;
  }
} 