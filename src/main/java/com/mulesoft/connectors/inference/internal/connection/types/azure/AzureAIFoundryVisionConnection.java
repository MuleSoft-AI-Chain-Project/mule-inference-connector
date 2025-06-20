package com.mulesoft.connectors.inference.internal.connection.types.azure;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.VisionModelConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AzureAIFoundryVisionConnection extends VisionModelConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions?api-version={api-version}";
  public static final String AZURE_AI_FOUNDRY_URL = "https://{resource-name}.services.ai.azure.com/models";

  public AzureAIFoundryVisionConnection(HttpClient httpClient, ObjectMapper objectMapper, ParametersDTO parametersDTO,
                                        String azureAIFoundryResourceName, String azureAIFoundryApiVersion) {
    super(httpClient, objectMapper, parametersDTO,
          fetchApiURL(azureAIFoundryResourceName, azureAIFoundryApiVersion));
  }

  private static String fetchApiURL(String azureAIFoundryResourceName, String azureAIFoundryApiVersion) {
    String aifurlStr = AZURE_AI_FOUNDRY_URL + URI_CHAT_COMPLETIONS;
    aifurlStr = aifurlStr
        .replace("{resource-name}", azureAIFoundryResourceName)
        .replace("{api-version}", azureAIFoundryApiVersion);
    return aifurlStr;
  }
}
