package com.mulesoft.connectors.inference.internal.connection.provider.azure;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.values.OfValues;

import com.mulesoft.connectors.inference.internal.connection.parameters.TextGenerationConnectionParameters;
import com.mulesoft.connectors.inference.internal.connection.provider.TextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.types.azure.AzureAIFoundryTextGenerationConnection;
import com.mulesoft.connectors.inference.internal.llmmodels.azure.providers.AzureAIFoundryTextGenerationModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("azure-ai-foundry")
@DisplayName("Azure AI Foundry")
public class AzureAIFoundryTextGenerationConnectionProvider extends TextGenerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(AzureAIFoundryTextGenerationConnectionProvider.class);

  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(AzureAIFoundryTextGenerationModelNameProvider.class)
  private String azureModelName;

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @Optional
  @DisplayName("[Azure AI Foundry] Resource Name")
  @Placement(order = 2)
  private String azureAIFoundryResourceName;

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @Optional
  @DisplayName("[Azure AI Foundry] API Version")
  @Placement(order = 3)
  private String azureAIFoundryApiVersion;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private TextGenerationConnectionParameters textGenerationConnectionParameters;

  @Override
  public AzureAIFoundryTextGenerationConnection connect() throws ConnectionException {
    logger.debug("AzureAIFoundryTextGenerationConnection connect ...");
    return new AzureAIFoundryTextGenerationConnection(getHttpClient(), getObjectMapper(), azureModelName,
                                                      textGenerationConnectionParameters.getApiKey(),
                                                      azureAIFoundryResourceName, azureAIFoundryApiVersion,
                                                      textGenerationConnectionParameters.getTemperature(),
                                                      textGenerationConnectionParameters.getTopP(),
                                                      textGenerationConnectionParameters.getMaxTokens(),
                                                      textGenerationConnectionParameters.getMcpSseServers(),
                                                      textGenerationConnectionParameters.getTimeout());
  }
}
