package com.mulesoft.connectors.inference.internal.connection.provider.nvidia;

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
import com.mulesoft.connectors.inference.internal.connection.types.nvidia.NvidiaTextGenerationConnection;
import com.mulesoft.connectors.inference.internal.llmmodels.nvidia.providers.NvidiaTextGenerationModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("nvidia")
@DisplayName("Nvidia")
public class NvidiaTextGenerationConnectionProvider extends TextGenerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(NvidiaTextGenerationConnectionProvider.class);

  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(NvidiaTextGenerationModelNameProvider.class)
  private String nvidiaModelName;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private TextGenerationConnectionParameters textGenerationConnectionParameters;

  @Override
  public NvidiaTextGenerationConnection connect() throws ConnectionException {
    logger.debug("NvidiaTextGenerationConnection connect ...");
    return new NvidiaTextGenerationConnection(getHttpClient(), getObjectMapper(), nvidiaModelName,
                                              textGenerationConnectionParameters.getApiKey(),
                                              textGenerationConnectionParameters.getTemperature(),
                                              textGenerationConnectionParameters.getTopP(),
                                              textGenerationConnectionParameters.getMaxTokens(),
                                              textGenerationConnectionParameters.getMcpSseServers(),
                                              textGenerationConnectionParameters.getTimeout());
  }
}
