package com.mulesoft.connectors.inference.internal.connection.provider.ibmwatson;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.values.OfValues;

import com.mulesoft.connectors.inference.internal.connection.parameters.TextGenerationConnectionParameters;
import com.mulesoft.connectors.inference.internal.connection.provider.TextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.types.ibmwatson.IBMWatsonTextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.llmmodels.ibmwatson.providers.IBMWatsonTextGenerationModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("ibmwatson")
@DisplayName("IBM Watson")
public class IBMWatsonTextGenerationConnectionProvider extends TextGenerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(IBMWatsonTextGenerationConnectionProvider.class);

  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(IBMWatsonTextGenerationModelNameProvider.class)
  private String ibmWatsonModelName;

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @Placement(order = 2)
  @Optional
  @DisplayName("[IBM Watson] API Version")
  private String ibmWatsonApiVersion;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private TextGenerationConnectionParameters textGenerationConnectionParameters;

  @Override
  public IBMWatsonTextGenerationConnection connect() throws ConnectionException {
    logger.debug("IBMWatsonTextGenerationConnection connect ...");
    return new IBMWatsonTextGenerationConnection(getHttpClient(), getObjectMapper(),
                                                 new ParametersDTO(ibmWatsonModelName,
                                                                   textGenerationConnectionParameters.getApiKey(),
                                                                   textGenerationConnectionParameters.getMaxTokens(),
                                                                   textGenerationConnectionParameters.getTemperature(),
                                                                   textGenerationConnectionParameters.getTopP(),
                                                                   textGenerationConnectionParameters.getTimeout()),
                                                 ibmWatsonApiVersion,
                                                 textGenerationConnectionParameters.getMcpSseServers());
  }
}
