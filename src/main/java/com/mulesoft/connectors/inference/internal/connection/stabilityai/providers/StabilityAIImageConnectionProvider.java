package com.mulesoft.connectors.inference.internal.connection.stabilityai.providers;

import com.mulesoft.connectors.inference.internal.connection.BaseConnection;
import com.mulesoft.connectors.inference.internal.connection.BaseConnectionParameters;
import com.mulesoft.connectors.inference.internal.connection.BaseConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.stabilityai.StabilityAIImageGenerationConnection;
import com.mulesoft.connectors.inference.internal.llmmodels.stabilityai.providers.StabilityAIImageModelNameProvider;
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

@Alias("stability-ai-image")
@DisplayName("Stability AI")
public class StabilityAIImageConnectionProvider extends BaseConnectionProvider {

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

    return new StabilityAIImageGenerationConnection(getHttpClient(), getObjectMapper(), stabilityAIModelName, baseConnectionParameters.getApiKey(),
            baseConnectionParameters.getTimeout(), getImageGenerationAPIURL(), "STABILITY_AI");
  }

  @Override
  public void disconnect(BaseConnection textGenerationConnection) {
    logger.debug("StabilityAIImageConnection disconnected ...");
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
    return STABILITY_AI_URL + URI_GENERATE_IMAGES;
  }
}
