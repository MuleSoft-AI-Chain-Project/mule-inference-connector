package com.mulesoft.connectors.internal.connection.openai.providers;

import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.internal.connection.TextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.VisionConnectionParameters;
import com.mulesoft.connectors.internal.connection.openai.OpenAIVisionConnection;
import com.mulesoft.connectors.internal.models.openai.providers.OpenAIVisionModelNameProvider;
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

@Alias("openai-vision")
@DisplayName("OpenAI")
public class OpenAIVisionConnectionProvider extends TextGenerationConnectionProvider {

    private static final Logger logger = LoggerFactory.getLogger(OpenAIVisionConnectionProvider.class);

    @Parameter
    @Placement(order = 1)
    @Expression(ExpressionSupport.SUPPORTED)
    @OfValues(OpenAIVisionModelNameProvider.class)
    private String openAIModelName;

    @ParameterGroup(name = Placement.CONNECTION_TAB)
    private VisionConnectionParameters visionConnectionParameters;

    @Override
    public OpenAIVisionConnection connect() throws ConnectionException {
        logger.debug("OpenAITextGenerationConnection connect ...");
        try {
            return new OpenAIVisionConnection(httpClient, openAIModelName,
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
