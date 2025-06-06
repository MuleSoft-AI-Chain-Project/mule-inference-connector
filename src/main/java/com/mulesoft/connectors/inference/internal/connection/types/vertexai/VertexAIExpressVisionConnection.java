package com.mulesoft.connectors.inference.internal.connection.types.vertexai;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.VisionModelConnection;

import com.fasterxml.jackson.databind.ObjectMapper;

public class VertexAIExpressVisionConnection extends VisionModelConnection {

  private static final String URI_CHAT_COMPLETIONS = "generateContent";
  public static final String VERTEX_AI_EXPRESS_URL = "https://aiplatform.googleapis.com/v1/publishers/google/models/{model_id}:";

  public VertexAIExpressVisionConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                                         Number temperature, Number topP,
                                         Number maxTokens, int timeout) {
    super(httpClient, objectMapper, apiKey, modelName, maxTokens, temperature, topP, timeout, fetchApiURL(modelName), "VERTEXAI");
  }

  private static String fetchApiURL(String modelName) {
    String vertexAIExpressUrlStr = VERTEX_AI_EXPRESS_URL + URI_CHAT_COMPLETIONS;
    vertexAIExpressUrlStr = vertexAIExpressUrlStr
        .replace("{model_id}", modelName);
    return vertexAIExpressUrlStr;
  }
}
