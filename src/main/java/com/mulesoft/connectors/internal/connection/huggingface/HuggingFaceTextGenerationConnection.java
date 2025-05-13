package com.mulesoft.connectors.internal.connection.huggingface;

import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import org.mule.runtime.http.api.client.HttpClient;

import java.util.Map;

public class HuggingFaceTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/models/{model-name}/v1/chat/completions";
  public static final String HUGGINGFACE_URL = "https://router.huggingface.co/hf-inference";

  public HuggingFaceTextGenerationConnection(HttpClient httpClient, String modelName, String apiKey,
                                         Number temperature, Number topP,
                                         Number maxTokens, Map<String, String> mcpSseServers, int timeout)
  {
    super(httpClient, apiKey, modelName, maxTokens, temperature, topP, timeout, mcpSseServers, fetchApiURL(modelName), "HUGGINGFACE");
  }

  @Override
  public Map<String, String> getQueryParams() {
    return Map.of();
  }

  @Override
  public Map<String, String> getAdditionalHeaders() {
    return Map.of("Authorization", "Bearer " + this.getApiKey());
  }

  private static String fetchApiURL(String modelName) {
    String urlStr = HUGGINGFACE_URL + URI_CHAT_COMPLETIONS;
    urlStr = urlStr
            .replace("{model-name}", modelName);
    return urlStr;
  }
} 