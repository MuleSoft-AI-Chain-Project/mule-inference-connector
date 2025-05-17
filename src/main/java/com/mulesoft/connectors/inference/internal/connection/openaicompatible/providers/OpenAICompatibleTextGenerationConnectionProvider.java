package com.mulesoft.connectors.inference.internal.connection.openaicompatible.providers;

import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnectionParameters;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.openaicompatible.OpenAICompatibleTextGenerationConnection;
import com.mulesoft.connectors.inference.internal.llmmodels.openai.providers.OpenAITextGenerationModelNameProvider;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.values.OfValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mulesoft.connectors.inference.internal.connection.openaicompatible.OpenAICompatibleTextGenerationConnection.OPENAI_COMPATIBLE_ENDPOINT;

@Alias("openai-compatible")
@DisplayName("OpenAI Compatible Endpoint")
public class OpenAICompatibleTextGenerationConnectionProvider extends TextGenerationConnectionProvider {

    private static final Logger logger = LoggerFactory.getLogger(OpenAICompatibleTextGenerationConnectionProvider.class);

    @Parameter
    @Placement(order = 1)
    @Expression(ExpressionSupport.SUPPORTED)
    @OfValues(OpenAITextGenerationModelNameProvider.class)
    @Optional(defaultValue = "gpt-4o-mini")
    private String openAICompatibleModelName;

    @Parameter
    @Expression(ExpressionSupport.SUPPORTED)
    @Optional(defaultValue = OPENAI_COMPATIBLE_ENDPOINT)
    @Placement(tab = "Additional Properties")
    @DisplayName("[OpenAI Compatible] Base URL")
    private String openAICompatibleURL;

    @ParameterGroup(name = Placement.CONNECTION_TAB)
    private TextGenerationConnectionParameters openAITextGenerationConnectionParameters;

    @Override
    public OpenAICompatibleTextGenerationConnection connect() throws ConnectionException {
        logger.debug("OpenAICompatibleTextGenerationConnection connect ...");
        return new OpenAICompatibleTextGenerationConnection(getHttpClient(),getObjectMapper(), openAICompatibleModelName, openAICompatibleURL,
                openAITextGenerationConnectionParameters.getApiKey(),
                openAITextGenerationConnectionParameters.getTemperature(), openAITextGenerationConnectionParameters.getTopP(),
                openAITextGenerationConnectionParameters.getMaxTokens(), openAITextGenerationConnectionParameters.getMcpSseServers(),
                openAITextGenerationConnectionParameters.getTimeout());
    }

    @Override
    public void disconnect(TextGenerationConnection baseConnection) {
        logger.debug(" OpenAICompatibleTextGenerationConnection disconnected ...");
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
