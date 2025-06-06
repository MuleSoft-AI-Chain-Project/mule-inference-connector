package com.mulesoft.connectors.inference.internal.connection.types.zhipuai;

import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ZhipuAITextGenerationConnection extends TextGenerationConnection {

  private static final String URI_CHAT_COMPLETIONS = "/chat/completions";
  public static final String ZHIPU_AI_URL = "https://open.bigmodel.cn/api/paas/v4";

  public ZhipuAITextGenerationConnection(HttpClient httpClient, ObjectMapper objectMapper, String modelName, String apiKey,
                                         Number temperature, Number topP,
                                         Number maxTokens, Map<String, String> mcpSseServers, int timeout) {
    super(httpClient, objectMapper, apiKey, modelName, maxTokens, temperature, topP, timeout, mcpSseServers, fetchApiURL(),
          "ZHIPUAI");
  }

  private static String fetchApiURL() {
    return ZHIPU_AI_URL + URI_CHAT_COMPLETIONS;
  }
}
