package com.mulesoft.connectors.inference.internal.connection.provider.deepinfra;

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
import com.mulesoft.connectors.inference.internal.connection.types.deepinfra.DeepInfraTextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.llmmodels.deepinfra.providers.DeepInfraTextGenerationModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("deepinfra")
@DisplayName("DeepInfra")
public class DeepInfraTextGenerationConnectionProvider extends TextGenerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(DeepInfraTextGenerationConnectionProvider.class);

  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(DeepInfraTextGenerationModelNameProvider.class)
  private String deepInfraModelName;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private TextGenerationConnectionParameters textGenerationConnectionParameters;

  @Override
  public DeepInfraTextGenerationConnection connect() throws ConnectionException {
    logger.debug("DeepInfraTextGenerationConnection connect ...");
    return new DeepInfraTextGenerationConnection(getHttpClient(), getObjectMapper(),
                                                 new ParametersDTO(deepInfraModelName,
                                                                   textGenerationConnectionParameters.getApiKey(),
                                                                   textGenerationConnectionParameters.getMaxTokens(),
                                                                   textGenerationConnectionParameters.getTemperature(),
                                                                   textGenerationConnectionParameters.getTopP(),
                                                                   textGenerationConnectionParameters.getTimeout()));
  }
}
