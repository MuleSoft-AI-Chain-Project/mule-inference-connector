package com.mulesoft.connectors.internal.connection.vertexai;

import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import org.mule.runtime.http.api.client.HttpClient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class VertexAIExpressVisionConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "generateContent";
  public static final String VERTEX_AI_EXPRESS_URL = "https://aiplatform.googleapis.com/v1/publishers/google/models/{model_id}:";

  public VertexAIExpressVisionConnection(HttpClient httpClient, String modelName, String apiKey,
                                         Number temperature, Number topP,
                                         Number maxTokens, int timeout)
          throws MalformedURLException {
    super(httpClient, apiKey, modelName, maxTokens, temperature, topP, timeout, null, fetchApiURL(modelName), "VERTEXAI");
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
    String vertexAIExpressUrlStr = VERTEX_AI_EXPRESS_URL + URI_CHAT_COMPLETIONS;
    vertexAIExpressUrlStr = vertexAIExpressUrlStr
            .replace("{model_id}", modelName);
    return vertexAIExpressUrlStr;
  }
} 