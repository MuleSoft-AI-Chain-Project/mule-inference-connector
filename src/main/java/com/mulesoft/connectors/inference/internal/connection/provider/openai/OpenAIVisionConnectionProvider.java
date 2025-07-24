package com.mulesoft.connectors.inference.internal.connection.provider.openai;

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
import com.mulesoft.connectors.inference.internal.connection.types.openai.OpenAIVisionConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.llmmodels.openai.providers.OpenAIVisionModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("openai-vision")
@DisplayName("OpenAI")
public class OpenAIVisionConnectionProvider extends VisionModelConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(OpenAIVisionConnectionProvider.class);

  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(OpenAIVisionModelNameProvider.class)
  private String openAIModelName;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private VisionConnectionParameters visionConnectionParameters;

  @Override
  public OpenAIVisionConnection connect() throws ConnectionException {
    logger.debug("OpenAIVisionConnection connect ...");
    return new OpenAIVisionConnection(getHttpClient(), getObjectMapper(), new ParametersDTO(
                                                                                            openAIModelName,
                                                                                            visionConnectionParameters
                                                                                                .getApiKey(),
                                                                                            visionConnectionParameters
                                                                                                .getMaxTokens(),
                                                                                            visionConnectionParameters
                                                                                                .getTemperature(),
                                                                                            visionConnectionParameters.getTopP(),
                                                                                            visionConnectionParameters
                                                                                                .getTimeout()));
  }
}
