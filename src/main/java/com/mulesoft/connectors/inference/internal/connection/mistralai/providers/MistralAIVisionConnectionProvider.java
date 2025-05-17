package com.mulesoft.connectors.inference.internal.connection.mistralai.providers;

import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.VisionConnectionParameters;
import com.mulesoft.connectors.inference.internal.connection.mistralai.MistralAIVisionConnection;
import com.mulesoft.connectors.inference.internal.llmmodels.mistral.providers.MistralAIVisionModelNameProvider;
import org.mule.runtime.api.connection.ConnectionException;
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

@Alias("mistralai-vision")
@DisplayName("Mistral AI")
public class MistralAIVisionConnectionProvider extends TextGenerationConnectionProvider {

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
        return new MistralAIVisionConnection(getHttpClient(),getObjectMapper(), mistralAIModelName,
                visionConnectionParameters.getApiKey(),
                visionConnectionParameters.getTemperature(),
                visionConnectionParameters.getTopP(),
                visionConnectionParameters.getMaxTokens(),
                visionConnectionParameters.getTimeout());
    }

    @Override
    public void disconnect(TextGenerationConnection baseConnection) {
        logger.debug(" MistralAIVisionConnection disconnected ...");
    }

    @Override
    public ConnectionValidationResult validate(TextGenerationConnection baseConnection) {

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
}
