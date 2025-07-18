package com.mulesoft.connectors.inference.internal.connection.provider.anthropic;

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
import com.mulesoft.connectors.inference.internal.connection.types.anthropic.AnthropicTextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.llmmodels.anthropic.providers.AnthropicTextGenerationModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("anthropic")
@DisplayName("Anthropic")
public class AnthropicTextGenerationConnectionProvider extends TextGenerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(AnthropicTextGenerationConnectionProvider.class);

  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(AnthropicTextGenerationModelNameProvider.class)
  private String anthropicModelName;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private TextGenerationConnectionParameters textGenerationConnectionParameters;

  @Override
  public AnthropicTextGenerationConnection connect() throws ConnectionException {
    logger.debug("AnthropicTextGenerationConnection connect ...");
    return new AnthropicTextGenerationConnection(getHttpClient(), getObjectMapper(),
                                                 new ParametersDTO(anthropicModelName,
                                                                   textGenerationConnectionParameters.getApiKey(),
                                                                   textGenerationConnectionParameters.getMaxTokens(),
                                                                   textGenerationConnectionParameters.getTemperature(),
                                                                   textGenerationConnectionParameters.getTopP(),
                                                                   textGenerationConnectionParameters.getTimeout()),
                                                 textGenerationConnectionParameters.getMcpSseServers());
  }
}
