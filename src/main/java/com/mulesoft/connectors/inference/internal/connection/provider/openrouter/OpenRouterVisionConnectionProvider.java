package com.mulesoft.connectors.inference.internal.connection.provider.openrouter;

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
import com.mulesoft.connectors.inference.internal.connection.types.openrouter.OpenRouterVisionConnection;
import com.mulesoft.connectors.inference.internal.llmmodels.openrouter.providers.OpenRouterVisionModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("openrouter-vision")
@DisplayName("OpenRouter")
public class OpenRouterVisionConnectionProvider extends VisionModelConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(OpenRouterVisionConnectionProvider.class);

  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(OpenRouterVisionModelNameProvider.class)
  private String openRouterModelName;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private VisionConnectionParameters visionConnectionParameters;

  @Override
  public OpenRouterVisionConnection connect() throws ConnectionException {
    logger.debug("OpenRouterVisionConnection connect ...");
    return new OpenRouterVisionConnection(getHttpClient(), getObjectMapper(), openRouterModelName,
                                          visionConnectionParameters.getApiKey(),
                                          visionConnectionParameters.getTemperature(),
                                          visionConnectionParameters.getTopP(),
                                          visionConnectionParameters.getMaxTokens(),
                                          visionConnectionParameters.getTimeout());
  }
}
