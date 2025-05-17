package com.mulesoft.connectors.inference.internal.connection.azure.providers;

import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnectionParameters;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.azure.AzureOpenAITextGenerationConnection;
import com.mulesoft.connectors.inference.internal.llmmodels.azure.providers.AzureOpenAITextGenerationModelNameProvider;
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

@Alias("azure-openai")
@DisplayName("Azure OpenAI")
public class AzureOpenAITextGenerationConnectionProvider extends TextGenerationConnectionProvider {

    private static final Logger logger = LoggerFactory.getLogger(AzureOpenAITextGenerationConnectionProvider.class);

    @Parameter
    @Placement(order = 1)
    @Expression(ExpressionSupport.SUPPORTED)
    @OfValues(AzureOpenAITextGenerationModelNameProvider.class)
    private String azureModelName;

    @Parameter
    @Expression(ExpressionSupport.SUPPORTED)
    @Optional
    @DisplayName("[Azure OpenAI] Resource Name")
    private String azureOpenaiResourceName;

    @Parameter
    @Expression(ExpressionSupport.SUPPORTED)
    @Optional
    @DisplayName("[Azure OpenAI] Deployment ID")
    private String azureOpenaiDeploymentId;

    @ParameterGroup(name = Placement.CONNECTION_TAB)
    private TextGenerationConnectionParameters textGenerationConnectionParameters;

    @Override
    public AzureOpenAITextGenerationConnection connect() throws ConnectionException {
        logger.debug("AzureTextGenerationConnection connect ...");
            return new AzureOpenAITextGenerationConnection(getHttpClient(),getObjectMapper(), azureModelName,
                    azureOpenaiResourceName, azureOpenaiDeploymentId,
                    textGenerationConnectionParameters.getApiKey(),
                    textGenerationConnectionParameters.getTemperature(), textGenerationConnectionParameters.getTopP(),
                    textGenerationConnectionParameters.getMaxTokens(), textGenerationConnectionParameters.getMcpSseServers(),
                    textGenerationConnectionParameters.getTimeout());
    }

    @Override
    public void disconnect(TextGenerationConnection baseConnection) {
        logger.debug("AzureTextGenerationConnection disconnected ...");
    }

    @Override
    public ConnectionValidationResult validate(TextGenerationConnection baseConnection) {
        logger.debug("Validating connection... ");
        try {
            return ConnectionValidationResult.success();
        } catch (Exception e) {
            return ConnectionValidationResult.failure("Failed to validate connection to Azure", e);
        }
    }
} 