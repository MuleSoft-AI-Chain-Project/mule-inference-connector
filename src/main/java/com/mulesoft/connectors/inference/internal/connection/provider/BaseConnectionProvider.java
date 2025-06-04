package com.mulesoft.connectors.inference.internal.connection.provider;

import static org.mule.runtime.api.meta.ExpressionSupport.NOT_SUPPORTED;
import static org.mule.runtime.core.api.lifecycle.LifecycleUtils.initialiseIfNeeded;

import static java.util.Optional.ofNullable;

import org.mule.runtime.api.lifecycle.Disposable;
import org.mule.runtime.api.lifecycle.Initialisable;
import org.mule.runtime.api.lifecycle.InitialisationException;
import org.mule.runtime.api.tls.TlsContextFactory;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.RefName;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.http.api.HttpService;
import org.mule.runtime.http.api.client.HttpClient;
import org.mule.runtime.http.api.client.HttpClientConfiguration;

import com.mulesoft.connectors.inference.internal.config.proxy.HttpProxyConfig;
import com.mulesoft.connectors.inference.internal.utils.ObjectMapperProvider;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseConnectionProvider implements Initialisable, Disposable {

  private static final Logger logger = LoggerFactory.getLogger(BaseConnectionProvider.class);

  @RefName
  private String configName;

  @Parameter
  @Optional
  @Placement(tab = "Proxy", order = 3)
  @Expression(NOT_SUPPORTED)
  @DisplayName("Proxy Configuration")
  private HttpProxyConfig proxyConfig;

  @Expression(NOT_SUPPORTED)
  @Placement(tab = "Security", order = 1)
  @Parameter
  @Optional
  @DisplayName("TLS Configuration")
  private TlsContextFactory tlsContextFactory;

  @Inject
  private HttpService httpService;

  private HttpClient httpClient;
  private ObjectMapper objectMapper;

  @Override
  public void initialise() throws InitialisationException {

    initialiseIfNeeded(tlsContextFactory);

    logger.debug("Starting httpClient...");

    httpClient = httpService.getClientFactory().create(createClientConfiguration());
    httpClient.start();
    objectMapper = ObjectMapperProvider.create();
  }

  @Override
  public void dispose() {
    logger.debug("Stopping httpClient...");
    ofNullable(httpClient).ifPresent(HttpClient::stop);
  }

  public HttpClient getHttpClient() {
    return httpClient;
  }

  public ObjectMapper getObjectMapper() {
    return objectMapper;
  }

  public HttpProxyConfig getProxyConfig() {
    return proxyConfig;
  }

  public TlsContextFactory getTlsContextFactory() {
    return tlsContextFactory;
  }

  private HttpClientConfiguration createClientConfiguration() {

    return new HttpClientConfiguration.Builder().setName(configName)
        .setTlsContextFactory(tlsContextFactory)
        .setProxyConfig(proxyConfig)
        .build();
  }
}
