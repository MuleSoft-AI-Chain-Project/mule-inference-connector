package com.mulesoft.connectors.inference.internal.connection.provider.cohere;

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
import com.mulesoft.connectors.inference.internal.connection.types.cohere.CohereTextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.llmmodels.cohere.providers.CohereTextGenerationModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("cohere")
@DisplayName("Cohere")
public class CohereTextGenerationConnectionProvider extends TextGenerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(CohereTextGenerationConnectionProvider.class);

  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(CohereTextGenerationModelNameProvider.class)
  private String cohereModelName;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private TextGenerationConnectionParameters textGenerationConnectionParameters;

  @Override
  public CohereTextGenerationConnection connect() throws ConnectionException {
    logger.debug("CohereTextGenerationConnection connect ...");
    return new CohereTextGenerationConnection(getHttpClient(), getObjectMapper(),
                                              new ParametersDTO(cohereModelName,
                                                                textGenerationConnectionParameters.getApiKey(),
                                                                textGenerationConnectionParameters.getMaxTokens(),
                                                                textGenerationConnectionParameters.getTemperature(),
                                                                textGenerationConnectionParameters.getTopP(),
                                                                textGenerationConnectionParameters.getTimeout()),
                                              textGenerationConnectionParameters.getMcpSseServers());
  }
}
