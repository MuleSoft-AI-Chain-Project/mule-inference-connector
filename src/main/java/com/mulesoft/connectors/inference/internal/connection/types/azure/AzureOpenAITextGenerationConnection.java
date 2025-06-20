package com.mulesoft.connectors.inference.internal.connection.types.azure;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.helpers.payload.AzureOpenAIRequestPayloadHelper;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AzureOpenAITextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions?api-version=2024-10-21";
  public static final String AZURE_OPENAI_URL = "https://{resource-name}.openai.azure.com/openai/deployments/{deployment-id}";

  private AzureOpenAIRequestPayloadHelper requestPayloadHelper;

  public AzureOpenAITextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, ParametersDTO parametersDTO,
                                             String azureOpenaiResourceName, String azureOpenaiDeploymentId,
                                             Map<String, String> mcpSseServers) {
    super(httpClient, objectMapper, parametersDTO, mcpSseServers,
          fetchApiURL(azureOpenaiResourceName, azureOpenaiDeploymentId));
  }

  @Override
  public AzureOpenAIRequestPayloadHelper getRequestPayloadHelper() {
    if (requestPayloadHelper == null)
      requestPayloadHelper = new AzureOpenAIRequestPayloadHelper(getObjectMapper());
    return requestPayloadHelper;
  }

  @Override
  public Map<String, String> getAdditionalHeaders() {
    return Map.of("api-key", this.getApiKey());
  }

  private static String fetchApiURL(String openaiResourceName, String openaiDeploymentId) {

    String urlStr = AZURE_OPENAI_URL + URI_CHAT_COMPLETIONS;
    urlStr = urlStr
        .replace("{resource-name}", openaiResourceName)
        .replace("{deployment-id}", openaiDeploymentId);
    return urlStr;
  }
}
