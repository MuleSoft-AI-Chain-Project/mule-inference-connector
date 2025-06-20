package com.mulesoft.connectors.inference.internal.connection.provider.mistralai;

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
import com.mulesoft.connectors.inference.internal.connection.types.mistralai.MistralAIVisionConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.llmmodels.mistral.providers.MistralAIVisionModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("mistralai-vision")
@DisplayName("Mistral AI")
public class MistralAIVisionConnectionProvider extends VisionModelConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(MistralAIVisionConnectionProvider.class);

  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(MistralAIVisionModelNameProvider.class)
  private String mistralAIModelName;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private VisionConnectionParameters visionConnectionParameters;

  @Override
  public MistralAIVisionConnection connect() throws ConnectionException {
    logger.debug("MistralAIVisionConnection connect ...");
    return new MistralAIVisionConnection(getHttpClient(), getObjectMapper(), new ParametersDTO(
                                                                                               mistralAIModelName,
                                                                                               visionConnectionParameters
                                                                                                   .getApiKey(),
                                                                                               visionConnectionParameters
                                                                                                   .getMaxTokens(),
                                                                                               visionConnectionParameters
                                                                                                   .getTemperature(),
                                                                                               visionConnectionParameters
                                                                                                   .getTopP(),
                                                                                               visionConnectionParameters
                                                                                                   .getTimeout()));
  }
}
