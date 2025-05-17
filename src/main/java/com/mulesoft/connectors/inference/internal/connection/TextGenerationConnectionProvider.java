package com.mulesoft.connectors.inference.internal.connection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.internal.api.proxy.HttpProxyConfig;
import com.mulesoft.connectors.inference.internal.helpers.ObjectMapperProvider;
import org.mule.runtime.api.connection.CachedConnectionProvider;
import org.mule.runtime.api.lifecycle.Disposable;
import org.mule.runtime.api.lifecycle.Initialisable;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import static java.util.Optional.ofNullable;
import static org.mule.runtime.api.meta.ExpressionSupport.NOT_SUPPORTED;

public abstract class TextGenerationConnectionProvider implements CachedConnectionProvider<TextGenerationConnection>, Initialisable, Disposable {

    private static final Logger logger = LoggerFactory.getLogger(TextGenerationConnectionProvider.class);

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
    public void initialise() {
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

    protected HttpProxyConfig getProxyConfig() {
        return proxyConfig;
    }

    protected TlsContextFactory getTlsContextFactory() {
        return tlsContextFactory;
    }

    protected HttpClient getHttpClient() {
        return httpClient;
    }

    protected ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    private HttpClientConfiguration createClientConfiguration() {

        return new HttpClientConfiguration.Builder().setName(configName)
                .setTlsContextFactory(getTlsContextFactory())
                .setProxyConfig(getProxyConfig())
                .build();
    }
}
