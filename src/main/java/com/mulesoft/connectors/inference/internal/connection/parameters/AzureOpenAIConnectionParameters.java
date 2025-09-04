package com.mulesoft.connectors.inference.internal.connection.parameters;

import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Example;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.param.display.Summary;

public class AzureOpenAIConnectionParameters {

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @Optional
  @DisplayName("[Azure OpenAI] Endpoint")
  @Example("https://<resource-name>.openai.azure.com/openai/deployments/<deployment-id>")
  @Placement(order = 2)
  @Summary("The complete endpoint for the Azure OpenAI service with corresponding values of resource-name and deployment-id.")
  private String azureOpenAiEndpoint;

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @Optional
  @DisplayName("[Azure OpenAI] Resource Name")
  @Placement(order = 3)
  @Summary("The resource name of the Azure OpenAI service used to construct the endpoint.")
  private String azureOpenaiResourceName;

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @Optional
  @DisplayName("[Azure OpenAI] Deployment ID")
  @Placement(order = 4)
  @Summary("The Deployment ID of the Azure OpenAI service used to construct the endpoint.")
  private String azureOpenaiDeploymentId;

  public String getAzureOpenAiEndpoint() {
    return azureOpenAiEndpoint;
  }

  public String getAzureOpenaiResourceName() {
    return azureOpenaiResourceName;
  }

  public String getAzureOpenaiDeploymentId() {
    return azureOpenaiDeploymentId;
  }
}
