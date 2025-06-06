package com.mulesoft.connectors.inference.internal.connection.provider.anthropic;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.values.OfValues;

import com.mulesoft.connectors.inference.internal.connection.parameters.VisionConnectionParameters;
import com.mulesoft.connectors.inference.internal.connection.provider.VisionModelConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.types.anthropic.AnthropicVisionConnection;
import com.mulesoft.connectors.inference.internal.llmmodels.anthropic.providers.AnthropicVisionModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("anthropic-vision")
@DisplayName("Anthropic")
public class AnthropicVisionConnectionProvider extends VisionModelConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(AnthropicVisionConnectionProvider.class);

  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(AnthropicVisionModelNameProvider.class)
  private String anthropicModelName;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private VisionConnectionParameters visionConnectionParameters;

  @Override
  public AnthropicVisionConnection connect() throws ConnectionException {
    logger.debug("AnthropicVisionConnection connect ...");
    return new AnthropicVisionConnection(getHttpClient(), getObjectMapper(), anthropicModelName,
                                         visionConnectionParameters.getApiKey(),
                                         visionConnectionParameters.getTemperature(),
                                         visionConnectionParameters.getTopP(),
                                         visionConnectionParameters.getMaxTokens(),
                                         visionConnectionParameters.getTimeout());
  }
}
