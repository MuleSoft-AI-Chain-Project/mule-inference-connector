package com.mulesoft.connectors.inference.internal.connection.provider.ai21labs;

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
import com.mulesoft.connectors.inference.internal.connection.types.ai21labs.AI21LabsTextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.llmmodels.ai21labs.providers.AI21LabsTextGenerationModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("ai21labs")
@DisplayName("AI21Labs")
public class AI21LabsTextGenerationConnectionProvider extends TextGenerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(AI21LabsTextGenerationConnectionProvider.class);

  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(AI21LabsTextGenerationModelNameProvider.class)
  private String ai21LabsModelName;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private TextGenerationConnectionParameters textGenerationConnectionParameters;

  @Override
  public AI21LabsTextGenerationConnection connect() throws ConnectionException {
    logger.debug("AI21LabsTextGenerationConnection connect ...");
    return new AI21LabsTextGenerationConnection(getHttpClient(), getObjectMapper(),
                                                new ParametersDTO(ai21LabsModelName,
                                                                  textGenerationConnectionParameters.getApiKey(),
                                                                  textGenerationConnectionParameters.getMaxTokens(),
                                                                  textGenerationConnectionParameters.getTemperature(),
                                                                  textGenerationConnectionParameters.getTopP(),
                                                                  textGenerationConnectionParameters.getTimeout()),
                                                textGenerationConnectionParameters.getMcpSseServers());
  }
}
