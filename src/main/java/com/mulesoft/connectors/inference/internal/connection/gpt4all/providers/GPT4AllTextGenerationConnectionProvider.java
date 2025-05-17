package com.mulesoft.connectors.inference.internal.connection.gpt4all.providers;

import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnectionParameters;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.gpt4all.GPT4AllTextGenerationConnection;
import com.mulesoft.connectors.inference.internal.constants.InferenceConstants;
import com.mulesoft.connectors.inference.internal.llmmodels.gpt4all.providers.GPT4AllTextGenerationModelNameProvider;
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

@Alias("gpt4all")
@DisplayName("GPT4All")
public class GPT4AllTextGenerationConnectionProvider extends TextGenerationConnectionProvider {

    private static final Logger logger = LoggerFactory.getLogger(GPT4AllTextGenerationConnectionProvider.class);

    @Parameter
    @Placement(order = 1)
    @Expression(ExpressionSupport.SUPPORTED)
    @OfValues(GPT4AllTextGenerationModelNameProvider.class)
    private String gpt4allModelName;

    @Parameter
    @Expression(ExpressionSupport.SUPPORTED)
    @Optional(defaultValue = InferenceConstants.GPT4ALL_URL)
    @Placement(tab = "Additional Properties")
    @DisplayName("[GPT4ALL] Base URL")
    private String gpt4AllBaseURL;

    @ParameterGroup(name = Placement.CONNECTION_TAB)
    private TextGenerationConnectionParameters textGenerationConnectionParameters;

    @Override
    public GPT4AllTextGenerationConnection connect() throws ConnectionException {
        logger.debug("GPT4AllTextGenerationConnection connect ...");
            return new GPT4AllTextGenerationConnection(getHttpClient(),getObjectMapper(), gpt4allModelName,gpt4AllBaseURL,
                    textGenerationConnectionParameters.getApiKey(),
                    textGenerationConnectionParameters.getTemperature(), textGenerationConnectionParameters.getTopP(),
                    textGenerationConnectionParameters.getMaxTokens(), textGenerationConnectionParameters.getMcpSseServers(),
                    textGenerationConnectionParameters.getTimeout());
    }

    @Override
    public void disconnect(TextGenerationConnection baseConnection) {
        logger.debug("GPT4AllTextGenerationConnection disconnected ...");
    }

    @Override
    public ConnectionValidationResult validate(TextGenerationConnection baseConnection) {
        logger.debug("Validating connection... ");
        try {
            return ConnectionValidationResult.success();
        } catch (Exception e) {
            return ConnectionValidationResult.failure("Failed to validate connection to GPT4All", e);
        }
    }
} 