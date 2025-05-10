package com.mulesoft.connectors.internal.connection.types;

import com.mulesoft.connectors.internal.api.proxy.HttpProxyConfig;
import com.mulesoft.connectors.internal.models.moderation.ModerationNameProvider;
import com.mulesoft.connectors.internal.models.moderation.ModerationTypeProvider;
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
@Alias("moderation-model")
@DisplayName("Moderation Model")
public class ModerationProvider implements CachedConnectionProvider<ModerationBase>, Startable, Stoppable {

  private HttpClient httpClient;

  @RefName
  private String configName;

  @Inject
  private HttpService httpService;
  @Parameter
  @Placement(order = 1, tab = Placement.DEFAULT_TAB)
  @Expression(ExpressionSupport.SUPPORTED)
  @DisplayName("Inference Type")
  @OfValues(ModerationTypeProvider.class)
  private String inferenceType;

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @DisplayName("API Key")
  private String apiKey;

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(ModerationNameProvider.class)
  private String modelName;

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @DisplayName("Timeout (milliseconds)")
  @Optional(defaultValue = "#['600000']")
  private int timeout;

  @Parameter
  @Placement(order = 2, tab = "Advanced")
  @Optional
  private TlsContextFactory tlsContext;

  @Parameter
  @Optional
  @Placement(tab = "Proxy", order = 1)
  private HttpProxyConfig proxyConfig;

  @Override
  public ModerationBase connect() throws ConnectionException {
    return new ModerationBase(
            httpClient,
            timeout,
            inferenceType,
            apiKey,
            modelName
    );
  }

  @Override
  public void disconnect(ModerationBase moderation) {

  }

  @Override
  public ConnectionValidationResult validate(ModerationBase moderation) {
    try {
      moderation.validate();
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
