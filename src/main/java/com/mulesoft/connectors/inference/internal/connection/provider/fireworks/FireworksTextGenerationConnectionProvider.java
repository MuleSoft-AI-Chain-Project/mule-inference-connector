package com.mulesoft.connectors.inference.internal.connection.provider.fireworks;

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
import com.mulesoft.connectors.inference.internal.connection.types.fireworks.FireworksTextGenerationConnection;
import com.mulesoft.connectors.inference.internal.llmmodels.fireworks.providers.FireworksTextGenerationModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("fireworks")
@DisplayName("Fireworks")
public class FireworksTextGenerationConnectionProvider extends TextGenerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(FireworksTextGenerationConnectionProvider.class);

  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(FireworksTextGenerationModelNameProvider.class)
  private String fireworksModelName;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private TextGenerationConnectionParameters textGenerationConnectionParameters;

  @Override
  public FireworksTextGenerationConnection connect() throws ConnectionException {
    logger.debug("FireworksTextGenerationConnection connect ...");
    return new FireworksTextGenerationConnection(getHttpClient(), getObjectMapper(), fireworksModelName,
                                                 textGenerationConnectionParameters.getApiKey(),
                                                 textGenerationConnectionParameters.getTemperature(),
                                                 textGenerationConnectionParameters.getTopP(),
                                                 textGenerationConnectionParameters.getMaxTokens(),
                                                 textGenerationConnectionParameters.getMcpSseServers(),
                                                 textGenerationConnectionParameters.getTimeout());
  }
}
