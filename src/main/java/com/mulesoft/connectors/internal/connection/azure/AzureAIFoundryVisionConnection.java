package com.mulesoft.connectors.internal.connection.azure;

import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import org.mule.runtime.http.api.client.HttpClient;

import java.util.Map;

public class AzureAIFoundryVisionConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions?api-version={api-version}";
  public static final String AZURE_AI_FOUNDRY_URL = "https://{resource-name}.services.ai.azure.com/models";

  public AzureAIFoundryVisionConnection(HttpClient httpClient, String modelName, String apiKey,
                                        String azureAIFoundryResourceName, String azureAIFoundryApiVersion,
                                        Number temperature, Number topP,
                                        Number maxTokens, int timeout) {
    super( httpClient, apiKey, modelName, maxTokens, temperature, topP, timeout, null,fetchApiURL(azureAIFoundryResourceName,azureAIFoundryApiVersion),"OPENAI");
  }

  @Override
  public Map<String, String> getQueryParams() {
    return Map.of();
  }

  @Override
  public Map<String, String> getAdditionalHeaders() {
    return Map.of("Authorization", "Bearer " + this.getApiKey());
  }

  private static String fetchApiURL(String azureAIFoundryResourceName, String azureAIFoundryApiVersion) {
    String aifurlStr = AZURE_AI_FOUNDRY_URL + URI_CHAT_COMPLETIONS;
    aifurlStr = aifurlStr
            .replace("{resource-name}", azureAIFoundryResourceName)
            .replace("{api-version}", azureAIFoundryApiVersion);
    return aifurlStr;
  }
}
