package com.mulesoft.connectors.internal.connection.openrouter.providers;

import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.internal.connection.TextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.VisionConnectionParameters;
import com.mulesoft.connectors.internal.connection.openai.OpenAIVisionConnection;
import com.mulesoft.connectors.internal.connection.openrouter.OpenRouterVisionConnection;
import com.mulesoft.connectors.internal.models.openrouter.providers.OpenRouterVisionModelNameProvider;
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

import java.net.MalformedURLException;

@Alias("openrouter-vision")
@DisplayName("OpenRouter")
public class OpenRouterVisionConnectionProvider extends TextGenerationConnectionProvider {

    private static final Logger logger = LoggerFactory.getLogger(OpenRouterVisionConnectionProvider.class);

    @Parameter
    @Placement(tab = Placement.CONNECTION_TAB, order = 1)
    @Expression(ExpressionSupport.SUPPORTED)
    @OfValues(OpenRouterVisionModelNameProvider.class)
    private String openRouterModelName;

    @ParameterGroup(name = Placement.CONNECTION_TAB)
    private VisionConnectionParameters visionConnectionParameters;

    @Override
    public OpenRouterVisionConnection connect() throws ConnectionException {
        logger.debug("OpenAITextGenerationConnection connect ...");
        try {
            return new OpenRouterVisionConnection(httpClient, openRouterModelName,
                    visionConnectionParameters.getApiKey(),
                    visionConnectionParameters.getTemperature(),
                    visionConnectionParameters.getTopP(),
                    visionConnectionParameters.getMaxTokens(),
                    visionConnectionParameters.getTimeout());
        } catch (MalformedURLException e) {
            throw new ConnectionException("Invalid Open Compatible URL",e);
        }
    }

    @Override
    public void disconnect(TextGenerationConnection baseConnection) {
        logger.debug(" OpenAITextGenerationConnection disconnected ...");
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
