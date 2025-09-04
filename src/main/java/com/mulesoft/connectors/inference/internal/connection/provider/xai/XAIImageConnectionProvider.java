package com.mulesoft.connectors.inference.internal.connection.provider.xai;

import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.mule.runtime.extension.api.annotation.values.OfValues;

import com.mulesoft.connectors.inference.internal.connection.parameters.BaseConnectionParameters;
import com.mulesoft.connectors.inference.internal.connection.provider.ImageGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.types.xai.XAIImageGenerationConnection;
import com.mulesoft.connectors.inference.internal.llmmodels.xai.providers.XAIImageModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("xai-image")
@DisplayName("xAI")
public class XAIImageConnectionProvider extends ImageGenerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(XAIImageConnectionProvider.class);

  public static final String X_AI_URL = "https://api.x.ai/v1";
  public static final String URI_GENERATE_IMAGES = "/images/generations";

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(XAIImageModelNameProvider.class)
  @Summary("This inference model is currently supported as a beta feature. Please refer to the product documentation")
  @Placement(order = 1)
  private String xAiModelName;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private BaseConnectionParameters baseConnectionParameters;

  @Override
  public XAIImageGenerationConnection connect() {
    logger.debug("XAIImageConnection connect ...");

    return new XAIImageGenerationConnection(getHttpClient(), getObjectMapper(), xAiModelName,
                                            baseConnectionParameters.getApiKey(), baseConnectionParameters.getCustomHeaders(),
                                            baseConnectionParameters.getTimeout(), getImageGenerationAPIURL());
  }

  private String getImageGenerationAPIURL() {
    return X_AI_URL + URI_GENERATE_IMAGES;
  }
}
