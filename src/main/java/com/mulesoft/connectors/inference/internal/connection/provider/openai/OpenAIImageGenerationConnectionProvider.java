package com.mulesoft.connectors.inference.internal.connection.provider.openai;

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
import com.mulesoft.connectors.inference.internal.connection.types.openai.OpenAIImageGenerationConnection;
import com.mulesoft.connectors.inference.internal.llmmodels.openai.providers.OpenAIImageGenerationModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("openai-image")
@DisplayName("OpenAI")
public class OpenAIImageGenerationConnectionProvider extends ImageGenerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(OpenAIImageGenerationConnectionProvider.class);

  public static final String OPEN_AI_URL = "https://api.openai.com/v1";
  public static final String OPENAI_GENERATE_IMAGES = "/images/generations";

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(OpenAIImageGenerationModelNameProvider.class)
  @Placement(order = 1)
  private String openAIModelName;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private BaseConnectionParameters baseConnectionParameters;

  @Override
  public OpenAIImageGenerationConnection connect() {
    logger.debug("ImageGenerationConnection connect ...");

    return new OpenAIImageGenerationConnection(getHttpClient(), getObjectMapper(), openAIModelName,
                                               baseConnectionParameters.getApiKey(),
                                               baseConnectionParameters.getTimeout(), getImageGenerationAPIURL());
  }

  private String getImageGenerationAPIURL() {
    return OPEN_AI_URL + OPENAI_GENERATE_IMAGES;
  }
}
