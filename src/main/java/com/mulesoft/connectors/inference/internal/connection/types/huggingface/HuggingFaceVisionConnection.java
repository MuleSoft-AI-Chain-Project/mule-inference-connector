package com.mulesoft.connectors.inference.internal.connection.types.huggingface;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.VisionModelConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HuggingFaceVisionConnection extends VisionModelConnection {

  private static final String URI_CHAT_COMPLETIONS = "/models/{model-name}/v1/chat/completions";
  public static final String HUGGINGFACE_URL = "https://router.huggingface.co/hf-inference";

  public HuggingFaceVisionConnection(HttpClient httpClient, ObjectMapper objectMapper,
                                     ParametersDTO parametersDTO) {
    super(httpClient, objectMapper, parametersDTO, fetchApiURL(parametersDTO.modelName()));
  }

  private static String fetchApiURL(String modelName) {
    String urlStr = HUGGINGFACE_URL + URI_CHAT_COMPLETIONS;
    urlStr = urlStr
        .replace("{model-name}", modelName);
    return urlStr;
  }
}
