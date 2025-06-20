package com.mulesoft.connectors.inference.internal.connection.provider.openrouter;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.values.OfValues;

import com.mulesoft.connectors.inference.internal.connection.parameters.TextGenerationConnectionParameters;
import com.mulesoft.connectors.inference.internal.connection.provider.TextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.types.openrouter.OpenRouterTextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.llmmodels.openrouter.providers.OpenRouterTextGenerationModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("openrouter")
@DisplayName("OpenRouter")
public class OpenRouterTextGenerationConnectionProvider extends TextGenerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(OpenRouterTextGenerationConnectionProvider.class);

  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(OpenRouterTextGenerationModelNameProvider.class)
  private String openRouterModelName;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private TextGenerationConnectionParameters textGenerationConnectionParameters;

  @Override
  public OpenRouterTextGenerationConnection connect() throws ConnectionException {
    logger.debug("OpenRouterTextGenerationConnection connect ...");
    return new OpenRouterTextGenerationConnection(getHttpClient(), getObjectMapper(),
                                                  new ParametersDTO(openRouterModelName,
                                                                    textGenerationConnectionParameters.getApiKey(),
                                                                    textGenerationConnectionParameters.getMaxTokens(),
                                                                    textGenerationConnectionParameters.getTemperature(),
                                                                    textGenerationConnectionParameters.getTopP(),
                                                                    textGenerationConnectionParameters.getTimeout()),
                                                  textGenerationConnectionParameters.getMcpSseServers());
  }
}
