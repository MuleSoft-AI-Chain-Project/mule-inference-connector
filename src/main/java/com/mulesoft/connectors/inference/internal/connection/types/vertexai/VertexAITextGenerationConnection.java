package com.mulesoft.connectors.inference.internal.connection.types.vertexai;

import static com.mulesoft.connectors.inference.internal.helpers.payload.VertexAIRequestPayloadHelper.getAccessTokenFromServiceAccountKey;
import static com.mulesoft.connectors.inference.internal.helpers.payload.VertexAIRequestPayloadHelper.getProviderByModel;

import org.mule.runtime.extension.api.exception.ModuleException;
import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.error.InferenceErrorType;
import com.mulesoft.connectors.inference.internal.helpers.payload.VertexAIRequestPayloadHelper;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class VertexAITextGenerationConnection extends TextGenerationConnection {

  private static final String GEMINI_URI_CHAT_COMPLETIONS = "generateContent";
  public static final String ANTHROPIC_URI_CHAT_COMPLETIONS = "rawPredict";

  public static final String VERTEX_AI_GEMINI_URL =
      "https://{LOCATION_ID}-aiplatform.googleapis.com/v1/projects/{PROJECT_ID}/locations/{LOCATION_ID}/publishers/google/models/{MODEL_ID}:";
  public static final String VERTEX_AI_ANTHROPIC_URL =
      "https://{LOCATION_ID}-aiplatform.googleapis.com/v1/projects/{PROJECT_ID}/locations/{LOCATION_ID}/publishers/anthropic/models/{MODEL_ID}:";
  public static final String VERTEX_AI_META_URL =
      "https://{LOCATION_ID}-aiplatform.googleapis.com/v1beta1/projects/{PROJECT_ID}/locations/{LOCATION_ID}/endpoints/openapi/chat/completions";

  private VertexAIRequestPayloadHelper requestPayloadHelper;
  private final String vertexAIServiceAccountKey;

  public VertexAITextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper,
                                          ParametersDTO parametersDTO,
                                          String vertexAILocationId,
                                          String vertexAIProjectId, String vertexAIServiceAccountKey,
                                          Map<String, String> mcpSseServers) {
    super(httpClient, objectMapper, parametersDTO, mcpSseServers,
          fetchApiURL(parametersDTO.modelName(), vertexAILocationId, vertexAIProjectId));
    this.vertexAIServiceAccountKey = vertexAIServiceAccountKey;
  }

  @Override
  public VertexAIRequestPayloadHelper getRequestPayloadHelper() {
    if (requestPayloadHelper == null)
      requestPayloadHelper = new VertexAIRequestPayloadHelper(getObjectMapper());
    return requestPayloadHelper;
  }

  public String getVertexAIServiceAccountKey() {
    return vertexAIServiceAccountKey;
  }

  @Override
  public Map<String, String> getAdditionalHeaders() {
    return Map.of("Authorization", "Bearer " + getAccessTokenFromServiceAccountKey(this));
  }

  private static String fetchApiURL(String modelName, String vertexAILocationId,
                                    String vertexAIProjectId) {String provider=getProviderByModel(modelName);return switch(provider){case"Google"->getFormattedString(VERTEX_AI_GEMINI_URL+GEMINI_URI_CHAT_COMPLETIONS,modelName,vertexAILocationId,vertexAIProjectId);case"Anthropic"->getFormattedString(VERTEX_AI_ANTHROPIC_URL+ANTHROPIC_URI_CHAT_COMPLETIONS,modelName,vertexAILocationId,vertexAIProjectId);case"Meta"->getFormattedString(VERTEX_AI_META_URL,null,vertexAILocationId,vertexAIProjectId);default->throw new ModuleException("Unknown provider. Skipping... "+provider,InferenceErrorType.INVALID_PROVIDER);};}

  private static String getFormattedString(String vertexAIUrlStr, String modelName, String vertexAILocationId,
                                           String vertexAIProjectId) {
    vertexAIUrlStr = vertexAIUrlStr
        .replace("{LOCATION_ID}", vertexAILocationId)
        .replace("{PROJECT_ID}", vertexAIProjectId);

    if (modelName != null)
      vertexAIUrlStr = vertexAIUrlStr
          .replace("{MODEL_ID}", modelName);
    return vertexAIUrlStr;
  }
}
