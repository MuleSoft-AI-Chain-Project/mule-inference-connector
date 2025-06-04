package com.mulesoft.connectors.inference.internal.connection.provider.perplexity;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.values.OfValues;

import com.mulesoft.connectors.inference.internal.connection.parameters.TextGenerationConnectionParameters;
import com.mulesoft.connectors.inference.internal.connection.provider.TextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.types.perplexity.PerplexityTextGenerationConnection;
import com.mulesoft.connectors.inference.internal.llmmodels.perplexity.providers.PerplexityTextGenerationModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("perplexity")
@DisplayName("Perplexity")
public class PerplexityTextGenerationConnectionProvider extends TextGenerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(PerplexityTextGenerationConnectionProvider.class);

  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(PerplexityTextGenerationModelNameProvider.class)
  private String perplexityModelName;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private TextGenerationConnectionParameters textGenerationConnectionParameters;

  @Override
  public PerplexityTextGenerationConnection connect() throws ConnectionException {
    logger.debug("PerplexityTextGenerationConnection connect ...");
    return new PerplexityTextGenerationConnection(getHttpClient(), getObjectMapper(), perplexityModelName,
                                                  textGenerationConnectionParameters.getApiKey(),
                                                  textGenerationConnectionParameters.getTemperature(),
                                                  textGenerationConnectionParameters.getTopP(),
                                                  textGenerationConnectionParameters.getMaxTokens(),
                                                  textGenerationConnectionParameters.getMcpSseServers(),
                                                  textGenerationConnectionParameters.getTimeout());
  }
}
