package com.mulesoft.connectors.inference.internal.connection.provider.cerebras;

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
import com.mulesoft.connectors.inference.internal.connection.types.cerebras.CerebrasTextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.llmmodels.cerebras.providers.CerebrasTextGenerationModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("cerebras")
@DisplayName("Cerebras")
public class CerebrasTextGenerationConnectionProvider extends TextGenerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(CerebrasTextGenerationConnectionProvider.class);

  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(CerebrasTextGenerationModelNameProvider.class)
  private String cerebrasModelName;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private TextGenerationConnectionParameters textGenerationConnectionParameters;

  @Override
  public CerebrasTextGenerationConnection connect() throws ConnectionException {
    logger.debug("CerebrasTextGenerationConnection connect ...");
    return new CerebrasTextGenerationConnection(getHttpClient(), getObjectMapper(),
                                                new ParametersDTO(cerebrasModelName,
                                                                  textGenerationConnectionParameters.getApiKey(),
                                                                  textGenerationConnectionParameters.getMaxTokens(),
                                                                  textGenerationConnectionParameters.getTemperature(),
                                                                  textGenerationConnectionParameters.getTopP(),
                                                                  textGenerationConnectionParameters.getTimeout()),
                                                textGenerationConnectionParameters.getMcpSseServers());
  }
}
