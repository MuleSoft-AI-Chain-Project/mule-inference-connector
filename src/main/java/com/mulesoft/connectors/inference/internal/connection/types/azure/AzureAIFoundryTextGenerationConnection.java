package com.mulesoft.connectors.inference.internal.connection.types.azure;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AzureAIFoundryTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions?api-version={api-version}";
  public static final String AZURE_AI_FOUNDRY_URL = "https://{resource-name}.services.ai.azure.com/models";

  public AzureAIFoundryTextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                                                String azureAIFoundryResourceName, String azureAIFoundryApiVersion,
                                                Number temperature, Number topP,
                                                Number maxTokens, Map<String, String> mcpSseServers, int timeout) {
    super(httpClient, objectMapper, apiKey, modelName, maxTokens, temperature, topP, timeout, mcpSseServers,
          fetchApiURL(azureAIFoundryResourceName, azureAIFoundryApiVersion), "AZURE");
  }

  @Override
  public Map<String, String> getAdditionalHeaders() {
    return Map.of("api-key", this.getApiKey());
  }

  private static String fetchApiURL(String azureAIFoundryResourceName, String azureAIFoundryApiVersion) {
    String aifurlStr = AZURE_AI_FOUNDRY_URL + URI_CHAT_COMPLETIONS;
    aifurlStr = aifurlStr
        .replace("{resource-name}", azureAIFoundryResourceName)
        .replace("{api-version}", azureAIFoundryApiVersion);
    return aifurlStr;
  }
}
