package com.mulesoft.connectors.inference.internal.connection.types.huggingface;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.connection.types.VisionModelConnection;
import org.mule.runtime.http.api.client.HttpClient;


public class HuggingFaceVisionConnection extends VisionModelConnection {

  private static final String URI_CHAT_COMPLETIONS = "/models/{model-name}/v1/chat/completions";
  public static final String HUGGINGFACE_URL = "https://router.huggingface.co/hf-inference";

  public HuggingFaceVisionConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                                     Number temperature, Number topP,
                                     Number maxTokens, int timeout)
  {
    super(httpClient, objectMapper, apiKey, modelName, maxTokens, temperature, topP, timeout,  fetchApiURL(modelName), "HUGGINGFACE");
  }

  private static String fetchApiURL(String modelName) {
    String urlStr = HUGGINGFACE_URL + URI_CHAT_COMPLETIONS;
    urlStr = urlStr
            .replace("{model-name}", modelName);
    return urlStr;
  }
} 