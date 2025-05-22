package com.mulesoft.connectors.inference.internal.connection.mistralai.providers;

import com.mulesoft.connectors.inference.internal.connection.BaseConnectionParameters;
import com.mulesoft.connectors.inference.internal.connection.ModerationConnection;
import com.mulesoft.connectors.inference.internal.connection.ModerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.llmmodels.mistral.providers.MistralAIModerationModelNameProvider;
import com.mulesoft.connectors.inference.internal.constants.InferenceConstants;
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

@Alias("mistralai-moderation")
@DisplayName("Mistral AI")
public class MistralAIModerationConnectionProvider extends ModerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(MistralAIModerationConnectionProvider.class);

  public static final String MISTRAL_AI_URL = "https://api.mistral.ai/v1";

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(MistralAIModerationModelNameProvider.class)
  @Placement(order = 1)
  private String mistralAIModelName;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private BaseConnectionParameters baseConnectionParameters;

  @Override
  public ModerationConnection connect() {
    logger.debug("ModerationConnection connect ...");

    return new ModerationConnection(getHttpClient(),getObjectMapper(), baseConnectionParameters.getApiKey(),
            mistralAIModelName, baseConnectionParameters.getTimeout(), getModerationAPIURL(),"MistralAI");
  }

  @Override
  public void disconnect(ModerationConnection textGenerationConnection) {
    logger.debug(" ModerationConnection disconnected ...");
  }

  @Override
  public ConnectionValidationResult validate(ModerationConnection textGenerationConnection) {

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

  private String getModerationAPIURL() {
    return MISTRAL_AI_URL + InferenceConstants.MODERATIONS_PATH;
  }
}
