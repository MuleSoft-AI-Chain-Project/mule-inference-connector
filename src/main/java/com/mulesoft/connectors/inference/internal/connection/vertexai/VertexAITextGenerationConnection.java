package com.mulesoft.connectors.inference.internal.connection.vertexai;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.exception.InferenceErrorType;
import com.mulesoft.connectors.inference.internal.helpers.payload.VertexAIRequestPayloadHelper;
import com.mulesoft.connectors.inference.internal.utils.ProviderUtils;
import org.mule.runtime.extension.api.exception.ModuleException;
import org.mule.runtime.http.api.client.HttpClient;

import java.util.Map;

import static com.mulesoft.connectors.inference.internal.utils.ConnectionUtils.getAccessTokenFromServiceAccountKey;

public class VertexAITextGenerationConnection extends TextGenerationConnection {

  private static final String GEMINI_URI_CHAT_COMPLETIONS = "generateContent";
  public static final String ANTHROPIC_URI_CHAT_COMPLETIONS = "rawPredict";

  public static final String VERTEX_AI_GEMINI_URL = "https://{LOCATION_ID}-aiplatform.googleapis.com/v1/projects/{PROJECT_ID}/locations/{LOCATION_ID}/publishers/google/models/{MODEL_ID}:";
  public static final String VERTEX_AI_ANTHROPIC_URL = "https://{LOCATION_ID}-aiplatform.googleapis.com/v1/projects/{PROJECT_ID}/locations/{LOCATION_ID}/publishers/anthropic/models/{MODEL_ID}:";
  public static final String VERTEX_AI_META_URL = "https://{LOCATION_ID}-aiplatform.googleapis.com/v1beta1/projects/{PROJECT_ID}/locations/{LOCATION_ID}/endpoints/openapi/chat/completions";

  private VertexAIRequestPayloadHelper requestPayloadHelper;
  private final String vertexAIServiceAccountKey;

  public VertexAITextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName,
                                          String vertexAILocationId,
                                          String vertexAIProjectId, String vertexAIServiceAccountKey, String apiKey,
                                          Number temperature, Number topP,
                                          Number maxTokens, Map<String, String> mcpSseServers, int timeout) {
    super(httpClient, objectMapper, apiKey, modelName, maxTokens, temperature, topP, timeout, mcpSseServers,
            fetchApiURL(modelName,vertexAILocationId,vertexAIProjectId), "VERTEXAI");
      this.vertexAIServiceAccountKey = vertexAIServiceAccountKey;
  }

  @Override
  public VertexAIRequestPayloadHelper getRequestPayloadHelper(){
    if(requestPayloadHelper == null)
      requestPayloadHelper = new VertexAIRequestPayloadHelper(getObjectMapper());
    return requestPayloadHelper;
  }

  public String getVertexAIServiceAccountKey() {
    return vertexAIServiceAccountKey;
  }

  @Override
  public Map<String, String> getAdditionalHeaders() {
    return Map.of("Authorization", "Bearer " + this.getApiKey());
  }

  private static String fetchApiURL(String modelName, String vertexAILocationId, String vertexAIProjectId) {
    String provider = ProviderUtils.getProviderByModel(modelName);
    return switch (provider) {
      case "Google"-> getFormattedString(VERTEX_AI_GEMINI_URL + GEMINI_URI_CHAT_COMPLETIONS,
              modelName, vertexAILocationId, vertexAIProjectId);
      case "Anthropic" -> getFormattedString(VERTEX_AI_ANTHROPIC_URL + ANTHROPIC_URI_CHAT_COMPLETIONS,
              modelName, vertexAILocationId, vertexAIProjectId);
      case "Meta" -> getFormattedString(VERTEX_AI_META_URL,null, vertexAILocationId, vertexAIProjectId);
      default-> throw new ModuleException("Unknown provider. Skipping... "+ provider, InferenceErrorType.INVALID_PROVIDER);
    };
  }

  private static String getFormattedString(String vertexAIUrlStr, String modelName, String vertexAILocationId, String vertexAIProjectId) {
    vertexAIUrlStr = vertexAIUrlStr
            .replace("{LOCATION_ID}", vertexAILocationId)
            .replace("{PROJECT_ID}", vertexAIProjectId);

    if(modelName!=null)
      vertexAIUrlStr = vertexAIUrlStr
              .replace("{MODEL_ID}", modelName);
    return vertexAIUrlStr;
  }
} 