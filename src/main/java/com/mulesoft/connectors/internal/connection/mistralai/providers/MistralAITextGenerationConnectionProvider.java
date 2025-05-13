package com.mulesoft.connectors.internal.connection.mistralai.providers;

import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.internal.connection.TextGenerationConnectionParameters;
import com.mulesoft.connectors.internal.connection.TextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.mistralai.MistralAITextGenerationConnection;
import com.mulesoft.connectors.internal.models.mistral.providers.MistralAITextGenerationModelNameProvider;
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

@Alias("mistralai")
@DisplayName("Mistral AI")
public class MistralAITextGenerationConnectionProvider extends TextGenerationConnectionProvider {

    private static final Logger logger = LoggerFactory.getLogger(MistralAITextGenerationConnectionProvider.class);

    @Parameter
    @Placement(order = 1)
    @Expression(ExpressionSupport.SUPPORTED)
    @OfValues(MistralAITextGenerationModelNameProvider.class)
    private String mistralAIModelName;

    @ParameterGroup(name = Placement.CONNECTION_TAB)
    private TextGenerationConnectionParameters textGenerationConnectionParameters;

    @Override
    public MistralAITextGenerationConnection connect() throws ConnectionException {
        logger.debug("OpenRouterTextGenerationConnection connect ...");
        try {
            return new MistralAITextGenerationConnection(httpClient, mistralAIModelName,
                    textGenerationConnectionParameters.getApiKey(),
                    textGenerationConnectionParameters.getTemperature(), textGenerationConnectionParameters.getTopP(),
                    textGenerationConnectionParameters.getMaxTokens(), textGenerationConnectionParameters.getMcpSseServers(),
                    textGenerationConnectionParameters.getTimeout());
        } catch (MalformedURLException e) {
            throw new ConnectionException("Invalid Open Compatible URL",e);
        }
    }

    @Override
    public void disconnect(TextGenerationConnection baseConnection) {
        logger.debug("OpenRouterTextGenerationConnection disconnected ...");
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
