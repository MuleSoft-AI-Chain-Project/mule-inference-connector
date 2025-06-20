package com.mulesoft.connectors.inference.internal.connection.provider.xai;

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
import com.mulesoft.connectors.inference.internal.connection.types.xai.XAIVisionConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.llmmodels.xai.providers.XAIVisionModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("xai-vision")
@DisplayName("xAI")
public class XAIVisionConnectionProvider extends VisionModelConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(XAIVisionConnectionProvider.class);

  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(XAIVisionModelNameProvider.class)
  private String xaiModelName;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private TextGenerationConnectionParameters textGenerationConnectionParameters;

  @Override
  public XAIVisionConnection connect() throws ConnectionException {
    logger.debug("XAIVisionConnection connect ...");
    return new XAIVisionConnection(getHttpClient(), getObjectMapper(), new ParametersDTO(
                                                                                         xaiModelName,
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
