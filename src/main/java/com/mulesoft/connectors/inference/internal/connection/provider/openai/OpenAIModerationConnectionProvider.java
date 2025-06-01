package com.mulesoft.connectors.inference.internal.connection.provider.openai;

import com.mulesoft.connectors.inference.internal.connection.BaseConnectionParameters;
import com.mulesoft.connectors.inference.internal.connection.ModerationConnection;
import com.mulesoft.connectors.inference.internal.connection.provider.ModerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.constants.InferenceConstants;
import com.mulesoft.connectors.inference.internal.llmmodels.openai.providers.OpenAIModerationModelNameProvider;
import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.values.OfValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("openai-moderation")
@DisplayName("OpenAI")
public class OpenAIModerationConnectionProvider extends ModerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(OpenAIModerationConnectionProvider.class);

  public static final String OPEN_AI_URL = "https://api.openai.com/v1";

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(OpenAIModerationModelNameProvider.class)
  @Placement(order = 1)
  private String openAIModelName;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private BaseConnectionParameters baseConnectionParameters;

  @Override
  public ModerationConnection connect() {
    logger.debug("ModerationConnection connect ...");

    return new ModerationConnection(getHttpClient(), getObjectMapper(), baseConnectionParameters.getApiKey(), openAIModelName,
            baseConnectionParameters.getTimeout(), getModerationAPIURL(), "OpenAI");
  }

  private String getModerationAPIURL() {
    return OPEN_AI_URL + InferenceConstants.MODERATIONS_PATH;
  }
}
