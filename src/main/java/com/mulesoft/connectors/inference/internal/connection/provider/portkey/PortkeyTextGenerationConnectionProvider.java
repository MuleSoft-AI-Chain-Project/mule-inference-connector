package com.mulesoft.connectors.inference.internal.connection.provider.portkey;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.mule.runtime.extension.api.annotation.values.OfValues;

import com.mulesoft.connectors.inference.internal.connection.parameters.TextGenerationConnectionParameters;
import com.mulesoft.connectors.inference.internal.connection.provider.TextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.types.portkey.PortkeyTextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.llmmodels.portkey.providers.PortkeyTextGenerationModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("portkey")
@DisplayName("Portkey")
public class PortkeyTextGenerationConnectionProvider extends TextGenerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(PortkeyTextGenerationConnectionProvider.class);

  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(PortkeyTextGenerationModelNameProvider.class)
  @Summary("This inference model is currently supported as a beta feature. Please refer to the product documentation")
  private String portkeyModelName;

  @Parameter
  @Optional
  @Expression(ExpressionSupport.SUPPORTED)
  @Placement(order = 2)
  @DisplayName("[Portkey] Virtual Key")
  private String virtualKey;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private TextGenerationConnectionParameters textGenerationConnectionParameters;

  @Override
  public PortkeyTextGenerationConnection connect() throws ConnectionException {
    logger.debug("PortkeyTextGenerationConnection connect ...");
    return new PortkeyTextGenerationConnection(getHttpClient(), getObjectMapper(),
                                               new ParametersDTO(portkeyModelName,
                                                                 textGenerationConnectionParameters.getApiKey(),
                                                                 textGenerationConnectionParameters.getMaxTokens(),
                                                                 textGenerationConnectionParameters.getTemperature(),
                                                                 textGenerationConnectionParameters.getTopP(),
                                                                 textGenerationConnectionParameters.getTimeout()),
                                               virtualKey);
  }
}
