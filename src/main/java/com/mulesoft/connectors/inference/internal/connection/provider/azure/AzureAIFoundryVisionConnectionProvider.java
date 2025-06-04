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

import com.mulesoft.connectors.inference.internal.connection.parameters.VisionConnectionParameters;
import com.mulesoft.connectors.inference.internal.connection.provider.VisionModelConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.types.azure.AzureAIFoundryVisionConnection;
import com.mulesoft.connectors.inference.internal.llmmodels.azure.providers.AzureAIFoundryVisionModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("azure-ai-foundry-vision")
@DisplayName("Azure AI Foundry")
public class AzureAIFoundryVisionConnectionProvider extends VisionModelConnectionProvider {

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
  @Placement(order = 2)
  private String azureAIFoundryResourceName;

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @Optional
  @DisplayName("[Azure AI Foundry] API Version")
  @Placement(order = 3)
  private String azureAIFoundryApiVersion;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private VisionConnectionParameters visionConnectionParameters;

  @Override
  public AzureAIFoundryVisionConnection connect() throws ConnectionException {
    logger.debug("AzureAIFoundryVisionConnection connect ...");
    return new AzureAIFoundryVisionConnection(getHttpClient(), getObjectMapper(), azureAIFoundryModelName,
                                              azureAIFoundryResourceName, azureAIFoundryApiVersion,
                                              visionConnectionParameters.getApiKey(),
                                              visionConnectionParameters.getTemperature(),
                                              visionConnectionParameters.getTopP(),
                                              visionConnectionParameters.getMaxTokens(),
                                              visionConnectionParameters.getTimeout());
  }
}
