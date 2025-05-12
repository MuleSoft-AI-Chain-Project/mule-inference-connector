package com.mulesoft.connectors.internal.connection.types;

import com.mulesoft.connectors.internal.api.proxy.HttpProxyConfig;
import com.mulesoft.connectors.internal.models.images.ModelNameProvider;
import com.mulesoft.connectors.internal.models.images.ModelTypeProvider;
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
@Alias("image-generation")
@DisplayName("Image Generation Model")
public class ImageGenerationProvider implements CachedConnectionProvider<ImageGeneration>, Startable, Stoppable {

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

  public void setInferenceType(String inferenceType) { this.inferenceType = inferenceType; }

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @DisplayName("API Key")
  private String apiKey;

  public void setApiKey(String apiKey) { this.apiKey = apiKey; }

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(ModelNameProvider.class)
  @Optional(defaultValue = "dall-e-3")
  private String modelName;

  public void setModelName(String modelName) { this.modelName = modelName; }

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @DisplayName("Timeout (milliseconds)")
  @Optional(defaultValue = "#['600000']")
  private int timeout;

  public void setTimeout(int timeout) { this.timeout = timeout; }

  @Parameter
  @Placement(order = 2, tab = "Advanced")
  @Optional
  private TlsContextFactory tlsContext;

  @Parameter
  @Optional
  @Placement(tab = "Proxy", order = 1)
  private HttpProxyConfig proxyConfig;


  @Override
  public ImageGeneration connect() throws ConnectionException {
    return new ImageGeneration(
            httpClient,
            timeout,
            inferenceType,
            apiKey,
            modelName
    );
  }

  @Override
  public void disconnect(ImageGeneration imageGeneration) {

  }

  @Override
  public ConnectionValidationResult validate(ImageGeneration imageGeneration) {
    try {
      imageGeneration.validate();
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
