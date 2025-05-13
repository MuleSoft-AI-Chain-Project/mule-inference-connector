package com.mulesoft.connectors.internal.connection.openai.providers;

import com.mulesoft.connectors.internal.connection.BaseConnection;
import com.mulesoft.connectors.internal.connection.BaseConnectionParameters;
import com.mulesoft.connectors.internal.connection.BaseConnectionProvider;
import com.mulesoft.connectors.internal.models.openai.providers.OpenAIImageGenerationModelNameProvider;
import org.mule.runtime.api.connection.ConnectionValidationResult;
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

@Alias("openai-image")
@DisplayName("OpenAI")
public class OpenAIImageGenerationConnectionProvider extends BaseConnectionProvider {

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
  public BaseConnection connect() {
    logger.debug("BaseConnection connect ...");

    return new BaseConnection(getHttpClient(), openAIModelName, baseConnectionParameters.getApiKey(),
            baseConnectionParameters.getTimeout(), getImageGenerationAPIURL(), "OPENAI");
  }

  @Override
  public void disconnect(BaseConnection textGenerationConnection) {
    logger.debug(" OpenAIImageConnection disconnected ...");
  }

  @Override
  public ConnectionValidationResult validate(BaseConnection textGenerationConnection) {

    logger.debug("Validating connection... ");
    try {
      //TODO implement proper call to validate connection is valid
      // if (textGenerationConnection.isValid()) {
      return ConnectionValidationResult.success();
     /* } else {
        return ConnectionValidationResult.failure("Failed to validate connection to PGVector", null);
      }*/
    } catch (Exception e) {
      return ConnectionValidationResult.failure("Failed to validate connection to PGVector", e);
    }
  }

  private String getImageGenerationAPIURL() {
    return OPEN_AI_URL + OPENAI_GENERATE_IMAGES;
  }

}
