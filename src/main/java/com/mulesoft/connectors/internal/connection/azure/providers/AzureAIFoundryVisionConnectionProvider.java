package com.mulesoft.connectors.internal.connection.azure.providers;

import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.internal.connection.TextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.VisionConnectionParameters;
import com.mulesoft.connectors.internal.connection.azure.AzureAIFoundryVisionConnection;
import com.mulesoft.connectors.internal.llmmodels.azure.providers.AzureAIFoundryVisionModelNameProvider;
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

@Alias("azure-ai-foundry-vision")
@DisplayName("Azure AI Foundry")
public class AzureAIFoundryVisionConnectionProvider extends TextGenerationConnectionProvider {

    private static final Logger logger = LoggerFactory.getLogger(AzureAIFoundryVisionConnectionProvider.class);

    @Parameter
    @Placement(order = 1)
    @Expression(ExpressionSupport.SUPPORTED)
    @OfValues(AzureAIFoundryVisionModelNameProvider.class)
    private String azureAIFoundryModelName;

    @Parameter
    @Expression(ExpressionSupport.SUPPORTED)
    @Optional
    @DisplayName("[Azure AI Foundry] Resource Name")
    private String azureAIFoundryResourceName;

    @Parameter
    @Expression(ExpressionSupport.SUPPORTED)
    @Optional
    @DisplayName("[Azure AI Foundry] API Version")
    private String azureAIFoundryApiVersion;

    @ParameterGroup(name = Placement.CONNECTION_TAB)
    private VisionConnectionParameters visionConnectionParameters;

    @Override
    public AzureAIFoundryVisionConnection connect() throws ConnectionException {
        logger.debug("AzureAIFoundryVisionConnection connect ...");
            return new AzureAIFoundryVisionConnection(httpClient, azureAIFoundryModelName,
                    azureAIFoundryResourceName, azureAIFoundryApiVersion,
                    visionConnectionParameters.getApiKey(),
                    visionConnectionParameters.getTemperature(),
                    visionConnectionParameters.getTopP(),
                    visionConnectionParameters.getMaxTokens(),
                    visionConnectionParameters.getTimeout());
    }

    @Override
    public void disconnect(TextGenerationConnection baseConnection) {
        logger.debug(" AzureAIFoundryVisionConnection disconnected ...");
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
