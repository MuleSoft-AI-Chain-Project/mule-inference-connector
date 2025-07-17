package com.mulesoft.connectors.inference.internal.connection.provider.heroku;

import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.values.OfValues;

import com.mulesoft.connectors.inference.internal.connection.parameters.BaseConnectionParameters;
import com.mulesoft.connectors.inference.internal.connection.provider.ImageGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.types.heroku.HerokuImageGenerationConnection;
import com.mulesoft.connectors.inference.internal.llmmodels.heroku.providers.HerokuImageGenerationModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("heroku-image")
@DisplayName("Heroku Managed Inference")
public class HerokuImageGenerationConnectionProvider extends ImageGenerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(HerokuImageGenerationConnectionProvider.class);

  public static final String HEROKU_GENERATE_IMAGES = "/v1/images/generations";

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(HerokuImageGenerationModelNameProvider.class)
  @Placement(order = 1)
  private String herokuInferenceModel;

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @Placement(order = 2)
  @Optional(defaultValue = "https://us.inference.heroku.com")
  @DisplayName("Heroku Diffusion URL")
  private String herokuDiffusionUrl;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private BaseConnectionParameters baseConnectionParameters;

  @Override
  public HerokuImageGenerationConnection connect() {
    logger.debug("ImageGenerationConnection connect ...");

    return new HerokuImageGenerationConnection(getHttpClient(), getObjectMapper(), herokuInferenceModel,
                                               baseConnectionParameters.getApiKey(),
                                               baseConnectionParameters.getTimeout(),
                                               getImageGenerationAPIURL(herokuDiffusionUrl));
  }

  private String getImageGenerationAPIURL(String herokuDiffusionUrl) {
    return herokuDiffusionUrl + HEROKU_GENERATE_IMAGES;
  }
}
