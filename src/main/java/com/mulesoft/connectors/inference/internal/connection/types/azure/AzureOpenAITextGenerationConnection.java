package com.mulesoft.connectors.inference.internal.connection.types.azure;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.helpers.payload.AzureOpenAIRequestPayloadHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

public class AzureOpenAITextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions?api-version={api-version}";
  public static final String AZURE_OPENAI_DEFAULT_URL = "https://{resource-name}/openai/deployments/{deployment-id}";

  private AzureOpenAIRequestPayloadHelper requestPayloadHelper;

  private final String user;

  public AzureOpenAITextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, ParametersDTO parametersDTO,
                                             String azureOpenAiEndpoint, String azureOpenaiResourceName,
                                             String azureOpenaiDeploymentId, String azureOpenaiApiVersion,
                                             String azureOpenaiUser) {
    super(httpClient, objectMapper, parametersDTO,
          fetchApiURL(azureOpenAiEndpoint, azureOpenaiResourceName, azureOpenaiDeploymentId, azureOpenaiApiVersion));
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

  private static String fetchApiURL(String azureOpenAiEndpoint, String openaiResourceName, String openaiDeploymentId,
                                    String azureOpenaiApiVersion) {

    String baseUrl = Optional.ofNullable(azureOpenAiEndpoint)
        .filter(StringUtils::isNotBlank)
        .orElseGet(() -> buildDefaultUrl(openaiResourceName, openaiDeploymentId));

    return (baseUrl + URI_CHAT_COMPLETIONS).replace("{api-version}", azureOpenaiApiVersion);
  }

  private static String buildDefaultUrl(String resourceName, String deploymentId) {
    return AZURE_OPENAI_DEFAULT_URL
        .replace("{resource-name}", resourceName)
        .replace("{deployment-id}", deploymentId);
  }

  public String getAzureOpenaiUser() {
    return user;
  }
}
