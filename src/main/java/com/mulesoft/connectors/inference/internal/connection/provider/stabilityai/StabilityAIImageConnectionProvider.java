package com.mulesoft.connectors.inference.internal.connection.provider.stabilityai;

import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.values.OfValues;

import com.mulesoft.connectors.inference.internal.connection.parameters.BaseConnectionParameters;
import com.mulesoft.connectors.inference.internal.connection.provider.ImageGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.types.stabilityai.StabilityAIImageGenerationConnection;
import com.mulesoft.connectors.inference.internal.llmmodels.stabilityai.providers.StabilityAIImageModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("stability-ai-image")
@DisplayName("Stability AI")
public class StabilityAIImageConnectionProvider extends ImageGenerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(StabilityAIImageConnectionProvider.class);

  public static final String STABILITY_AI_URL = "https://api.stability.ai";
  public static final String URI_GENERATE_IMAGES = "/v2beta/stable-image/generate/sd3";

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(StabilityAIImageModelNameProvider.class)
  @Placement(order = 1)
  private String stabilityAIModelName;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private BaseConnectionParameters baseConnectionParameters;

  @Override
  public StabilityAIImageGenerationConnection connect() {
    logger.debug("BaseConnection connect ...");

    return new StabilityAIImageGenerationConnection(getHttpClient(), getObjectMapper(), stabilityAIModelName,
                                                    baseConnectionParameters.getApiKey(),
                                                    baseConnectionParameters.getTimeout(), getImageGenerationAPIURL(),
                                                    "STABILITY_AI");
  }

  private String getImageGenerationAPIURL() {
    return STABILITY_AI_URL + URI_GENERATE_IMAGES;
  }
}
