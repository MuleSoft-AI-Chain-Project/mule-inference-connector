package com.mulesoft.connectors.inference.internal.connection.types.azure;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.helpers.payload.AzureOpenAIRequestPayloadHelper;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AzureOpenAITextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions?api-version=2024-10-21";
  public static final String AZURE_OPENAI_URI = "/openai/deployments/{deployment-id}";

  private AzureOpenAIRequestPayloadHelper requestPayloadHelper;

  private final String user;

  public AzureOpenAITextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, ParametersDTO parametersDTO,
                                             String azureOpenAiEndpoint, String azureOpenaiDeploymentId,
                                             String azureOpenaiUser) {
    super(httpClient, objectMapper, parametersDTO,
          fetchApiURL(azureOpenAiEndpoint, azureOpenaiDeploymentId));
    this.user = azureOpenaiUser;
  }

  @Override
  public AzureOpenAIRequestPayloadHelper getRequestPayloadHelper() {
    if (requestPayloadHelper == null)
      requestPayloadHelper = new AzureOpenAIRequestPayloadHelper(getObjectMapper());
    return requestPayloadHelper;
  }

  @Override
  public Map<String, String> getAdditionalHeaders() {
    Map<String, String> headers = new HashMap<>();

    headers.put("api-key", this.getApiKey());
    headers.putAll(getCustomHeadersMap());
    return headers;
  }

  private static String fetchApiURL(String azureOpenAiEndpoint, String openaiDeploymentId) {

    String urlStr = azureOpenAiEndpoint + AZURE_OPENAI_URI + URI_CHAT_COMPLETIONS;
    urlStr = urlStr
        .replace("{deployment-id}", openaiDeploymentId);
    return urlStr;
  }

  public String getAzureOpenaiUser() {
    return user;
  }
}
