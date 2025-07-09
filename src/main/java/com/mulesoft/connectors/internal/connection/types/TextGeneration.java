package com.mulesoft.connectors.internal.connection.types;

import com.mulesoft.connectors.internal.connection.ChatCompletionBase;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.http.api.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class TextGeneration implements ChatCompletionBase {

  private static Logger LOGGER = LoggerFactory.getLogger(TextGeneration.class);

  private HttpClient httpClient;
  private int timeout;
  private String inferenceType;
  private String apiKey;
  private String modelName;
  private Number maxTokens;
  private Number temperature;
  private Number topP;
  private String azureAIFoundryApiVersion;
  private String azureAIFoundryResourceName;
  private String azureOpenaiDeploymentId;
  private String azureOpenaiResourceName;
  private String azureOpenaiUser;
  private String dataBricksModelUrl;
  private String dockerModelUrl;
  private String gpt4All;
  private String ibmWatsonApiVersion;
  private String ibmWatsonProjectID;
  private String lmStudio;
  private String ollamaUrl;
  private String openCompatibleURL;
  private String virtualKey;
  private String xnferenceUrl;
  private String vertexAIProjectId;
  private String vertexAILocationId;
  private String vertexAIServiceAccountKey;
  private Map<String, String> mcpSseServers;

  public TextGeneration(
          HttpClient httpClient,
          int timeout,
          String inferenceType,
          String apiKey,
          String modelName,
          Number maxTokens,
          Number temperature,
          Number topP,
          String azureAIFoundryApiVersion,
          String azureAIFoundryResourceName,
          String azureOpenaiDeploymentId,
          String azureOpenaiResourceName,
          String azureOpenaiUser,
          String dataBricksModelUrl,
          String dockerModelUrl,
          String gpt4All,
          String ibmWatsonApiVersion,
          String ibmWatsonProjectID,
          String lmStudio,
          String ollamaUrl,
          String openCompatibleURL,
          String virtualKey,
          String xnferenceUrl,
          String vertexAIProjectId,
          String vertexAILocationId,
          String vertexAIServiceAccountKey,
          Map<String, String> mcpSseServers

  ) {

    this.httpClient = httpClient;
    this.timeout = timeout;
    this.inferenceType = inferenceType;
    this.apiKey = apiKey;
    this.modelName = modelName;
    this.maxTokens = maxTokens;
    this.temperature = temperature;
    this.topP = topP;
    this.azureAIFoundryApiVersion = azureAIFoundryApiVersion;
    this.azureAIFoundryResourceName = azureAIFoundryResourceName;
    this.azureOpenaiDeploymentId = azureOpenaiDeploymentId;
    this.azureOpenaiResourceName = azureOpenaiResourceName;
    this.azureOpenaiUser = azureOpenaiUser;
    this.dataBricksModelUrl = dataBricksModelUrl;
    this.dockerModelUrl = dockerModelUrl;
    this.gpt4All = gpt4All;
    this.ibmWatsonApiVersion = ibmWatsonApiVersion;
    this.ibmWatsonProjectID = ibmWatsonProjectID;
    this.lmStudio = lmStudio;
    this.ollamaUrl = ollamaUrl;
    this.openCompatibleURL = openCompatibleURL;
    this.virtualKey = virtualKey;
    this.xnferenceUrl = xnferenceUrl;
    this.vertexAIProjectId = vertexAIProjectId;
    this.vertexAILocationId = vertexAILocationId;
    this.vertexAIServiceAccountKey = vertexAIServiceAccountKey;
    this.mcpSseServers = mcpSseServers;


  }

  public HttpClient getHttpClient() {
    return httpClient;
  }


  public int getTimeout() {
    return timeout;
  }


  public String getInferenceType() {
    return inferenceType;
  }

  public String getApiKey() {
    return apiKey;
  }


  public String getModelName(){
    return modelName;
  }

  public Number getMaxTokens() {
    return maxTokens;
  }

  public Number getTemperature() {
    return temperature;
  }

  public Number getTopP() {
    return topP;
  }

  public String getAzureAIFoundryApiVersion() { return azureAIFoundryApiVersion; }

  public String getAzureAIFoundryResourceName() { return azureAIFoundryResourceName; }

  public String getAzureOpenaiDeploymentId() { return azureOpenaiDeploymentId; }

  public String getAzureOpenaiResourceName() { return azureOpenaiResourceName; }
  
  public String getAzureOpenaiUser() { return azureOpenaiUser; }

  public String getDataBricksModelUrl() { return dataBricksModelUrl; }

  public String getDockerModelUrl() { return dockerModelUrl; }

  public String getGpt4All() { return gpt4All; }

  public String getIBMWatsonApiVersion() { return ibmWatsonApiVersion; }

  public String getibmWatsonProjectID() { return ibmWatsonProjectID; }

  public String getLmStudio() { return lmStudio; }

  public String getOllamaUrl() { return ollamaUrl; }

  public String getOpenAICompatibleURL() { return openCompatibleURL; }

  public String getVirtualKey() { return virtualKey; }

  public String getxinferenceUrl() { return xnferenceUrl; }

  public String getVertexAIProjectId() { return vertexAIProjectId; }

  public String getVertexAILocationId() { return vertexAILocationId; }
  
  public String getVertexAIServiceAccountKey() { return vertexAIServiceAccountKey; }

  public Map<String, String> getMcpSseServers() {
    return mcpSseServers;
  }


  public boolean validate() throws ConnectionException {
    return true;
  }

}
