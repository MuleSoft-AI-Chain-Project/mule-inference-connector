package com.mulesoft.connectors.inference.internal.connection.types.databricks;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DatabricksTextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/serving-endpoints/{model_name}/invocations";

  public DatabricksTextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper,
                                            ParametersDTO parametersDTO, String databricksModelURL) {
    super(httpClient, objectMapper, parametersDTO,
          fetchApiURL(databricksModelURL, parametersDTO.modelName()));
  }

  private static String fetchApiURL(String databricksModelURL, String databricksModelName) {
    String dBricksUrlStr = databricksModelURL + URI_CHAT_COMPLETIONS;
    dBricksUrlStr = dBricksUrlStr
        .replace("{model_name}", databricksModelName);
    return dBricksUrlStr;
  }
}
