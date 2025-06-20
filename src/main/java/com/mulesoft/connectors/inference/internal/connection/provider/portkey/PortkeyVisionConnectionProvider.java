package com.mulesoft.connectors.inference.internal.connection.provider.portkey;

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
import com.mulesoft.connectors.inference.internal.connection.provider.VisionModelConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.types.portkey.PortkeyVisionConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.llmmodels.portkey.providers.PortkeyVisionModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("portkey-vision")
@DisplayName("Portkey")
public class PortkeyVisionConnectionProvider extends VisionModelConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(PortkeyVisionConnectionProvider.class);

  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(PortkeyVisionModelNameProvider.class)
  private String portkeyModelName;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private TextGenerationConnectionParameters textGenerationConnectionParameters;

  @Override
  public PortkeyVisionConnection connect() throws ConnectionException {
    logger.debug("PortkeyVisionConnection connect ...");
    return new PortkeyVisionConnection(getHttpClient(), getObjectMapper(), new ParametersDTO(
                                                                                             portkeyModelName,
                                                                                             textGenerationConnectionParameters
                                                                                                 .getApiKey(),
                                                                                             textGenerationConnectionParameters
                                                                                                 .getMaxTokens(),
                                                                                             textGenerationConnectionParameters
                                                                                                 .getTemperature(),
                                                                                             textGenerationConnectionParameters
                                                                                                 .getTopP(),
                                                                                             textGenerationConnectionParameters
                                                                                                 .getTimeout()));
  }
}
