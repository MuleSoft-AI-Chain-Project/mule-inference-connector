package com.mulesoft.connectors.internal.connection.types;

import com.mulesoft.connectors.internal.api.proxy.HttpProxyConfig;
import com.mulesoft.connectors.internal.constants.InferenceConstants;
import com.mulesoft.connectors.internal.models.vision.ModelNameProvider;
import com.mulesoft.connectors.internal.models.vision.ModelTypeProvider;
import org.mule.runtime.api.connection.CachedConnectionProvider;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.exception.MuleException;
import org.mule.runtime.api.lifecycle.Startable;
import org.mule.runtime.api.lifecycle.Stoppable;
import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.api.tls.TlsContextFactory;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.RefName;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.values.OfValues;
import org.mule.runtime.http.api.HttpService;
import org.mule.runtime.http.api.client.HttpClient;
import org.mule.runtime.http.api.client.HttpClientConfiguration;

import javax.inject.Inject;

@Deprecated
@Alias("vision")
@DisplayName("Vision Models")
public class VisionProvider implements CachedConnectionProvider<Vision>, Startable, Stoppable {

  private HttpClient httpClient;

  @RefName
  private String configName;

  @Inject
  private HttpService httpService;


  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @DisplayName("Inference Type")
  @OfValues(ModelTypeProvider.class)
  private String inferenceType;

  public String getInferenceType() { return inferenceType; }
  public void setInferenceType(String inferenceType) { this.inferenceType = inferenceType; }

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @DisplayName("API Key")
  private String apiKey;

  public void setApiKey(String apiKey) { this.apiKey = apiKey; }

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(ModelNameProvider.class)
  @Optional(defaultValue = "gpt-4o-mini")
  private String modelName;

  public String getModelName() { return modelName; }
  public void setModelName(String modelName) { this.modelName = modelName; }

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @Optional(defaultValue = "500")
  private Number maxTokens;

  public Number getMaxTokens() { return maxTokens; }
  public void setMaxTokens(Number maxTokens) { this.maxTokens = maxTokens; }

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @Optional(defaultValue = "0.9")
  private Number temperature;

  public Number getTemperature() { return temperature; }
  public void setTemperature(Number temperature) { this.temperature = temperature; }

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @Optional(defaultValue = "0.9")
  private Number topP;

  public Number getTopP() { return topP; }
  public void setTopP(Number topP) { this.topP = topP; }

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @DisplayName("Timeout (milliseconds)")
  @Optional(defaultValue = "60000")
  private int timeout;

  public int getTimeout() { return timeout; }
  public void setTimeout(int timeout) { this.timeout = timeout; }

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @Optional(defaultValue = "http://localhost:11434/api")
  @Placement(tab = "Additional Properties")
  @DisplayName("[Ollama] Base URL")
  private String ollamaUrl;

  public void setOllamaUrl(String ollamaUrl) { this.ollamaUrl = ollamaUrl; }

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @Optional(defaultValue = InferenceConstants.OPENAI_COMPATIBLE_ENDPOINT)
  @Placement(tab = "Additional Properties")
  @DisplayName("[OpenAI Compatible] Base URL")
  private String openCompatibleURL;

  public void setOpenAICompatibleURL(String openCompatibleURL) { this.openCompatibleURL = openCompatibleURL; }

  @Parameter
  @Optional(defaultValue = "Portkey-virtual-key")
  @Expression(ExpressionSupport.SUPPORTED)
  @Placement(tab = "Additional Properties")
  @DisplayName("[Portkey] Virtual Key")
  private String virtualKey;

  public void setVirtualKey(String virtualKey) { this.virtualKey = virtualKey; }

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @Optional(defaultValue = "us-central1")
  @Placement(tab = "Additional Properties")
  @DisplayName("[VertexAI] Location Id")
  private String vertexAILocationId;

  public void setVertexAILocationId(String vertexAILocationId) { this.vertexAILocationId = vertexAILocationId; }

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @Optional
  @Placement(tab = "Additional Properties")
  @DisplayName("[VertexAI] Project Id")
  private String vertexAIProjectId;

  public void setVertexAIProjectId(String vertexAIProjectId) { this.vertexAIProjectId = vertexAIProjectId; }
  
  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @Placement(tab = "Additional Properties")
  @Optional  
  @DisplayName("[VertexAI] Service Account Key")
  private String vertexAIServiceAccountKey;

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @Optional
  @Placement(tab = "Additional Properties")
  @DisplayName("[Azure AI Foundry] API Version")
  private String azureAIFoundryApiVersion;

  public void setAzureAIFoundryApiVersion(String azureAIFoundryApiVersion) { this.azureAIFoundryApiVersion = azureAIFoundryApiVersion; }

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @Optional
  @Placement(tab = "Additional Properties")
  @DisplayName("[Azure AI Foundry] Resource Name")
  private String azureAIFoundryResourceName;

  public void setAzureAIFoundryResourceName(String azureAIFoundryResourceName) { this.azureAIFoundryResourceName = azureAIFoundryResourceName; }


  @Parameter
  @Placement(order = 2, tab = "Advanced")
  @Optional
  private TlsContextFactory tlsContext;

  @Parameter
  @Optional
  @Placement(tab = "Proxy", order = 1)
  private HttpProxyConfig proxyConfig;


  @Override
  public Vision connect() throws ConnectionException {
    return new Vision(
            httpClient,
            timeout,
            inferenceType,
            apiKey,
            modelName,
            maxTokens,
            temperature,
            topP,
            ollamaUrl,
            openCompatibleURL,
            virtualKey,
            vertexAIProjectId,
            vertexAILocationId,
            vertexAIServiceAccountKey,
            azureAIFoundryApiVersion,
            azureAIFoundryResourceName

    );  }

  @Override
  public void disconnect(Vision vision) {

  }

  @Override
  public ConnectionValidationResult validate(Vision vision) {
    try {
      vision.validate();
      return ConnectionValidationResult.success();
    }
    catch (ConnectionException e) {
      return ConnectionValidationResult.failure(e.getMessage(), e);
    }
  }

  @Override
  public void start() throws MuleException {

    HttpClientConfiguration config = createClientConfiguration();
    httpClient = httpService.getClientFactory().create(config);
    httpClient.start();
  }

  private HttpClientConfiguration createClientConfiguration() {

    HttpClientConfiguration.Builder builder = new HttpClientConfiguration.Builder().setName(configName);
    if (null != tlsContext) {
      builder.setTlsContextFactory(tlsContext);
    } else {
      builder.setTlsContextFactory(TlsContextFactory.builder().buildDefault());
    }
    if (proxyConfig != null) {
      builder.setProxyConfig(proxyConfig);
    }
    return builder.build();
  }

  @Override
  public void stop() throws MuleException {
    if (httpClient != null) {
      httpClient.stop();
    }
  }
}
