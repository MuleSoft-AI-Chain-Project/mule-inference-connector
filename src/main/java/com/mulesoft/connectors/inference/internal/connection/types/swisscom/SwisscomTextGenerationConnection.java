package com.mulesoft.connectors.inference.internal.connection.types.swisscom;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.helpers.payload.OpenAIRequestPayloadHelper;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SwisscomTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String SWISSCOM_URL = "https://api.swisscom.com/layer/swiss-ai-platform/{modelName}/v1";
  private OpenAIRequestPayloadHelper requestPayloadHelper;

  public SwisscomTextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, ParametersDTO parametersDTO,
                                          String swisscomURL) {
    super(httpClient, objectMapper, parametersDTO, fetchApiURL(swisscomURL));
  }

  private static String fetchApiURL(String swisscomURL) {
    return swisscomURL + URI_CHAT_COMPLETIONS;
  }
}
