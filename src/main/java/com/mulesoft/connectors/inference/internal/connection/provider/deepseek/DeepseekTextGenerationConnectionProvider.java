package com.mulesoft.connectors.inference.internal.connection.provider.deepseek;

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
import com.mulesoft.connectors.inference.internal.connection.types.deepseek.DeepseekTextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.llmmodels.deepseek.providers.DeepseekTextGenerationModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("deepseek")
@DisplayName("Deepseek")
public class DeepseekTextGenerationConnectionProvider extends TextGenerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(DeepseekTextGenerationConnectionProvider.class);

  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(DeepseekTextGenerationModelNameProvider.class)
  private String deepseekModelName;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private TextGenerationConnectionParameters textGenerationConnectionParameters;

  @Override
  public DeepseekTextGenerationConnection connect() throws ConnectionException {
    logger.debug("DeepseekTextGenerationConnection connect ...");
    return new DeepseekTextGenerationConnection(getHttpClient(), getObjectMapper(),
                                                new ParametersDTO(deepseekModelName,
                                                                  textGenerationConnectionParameters.getApiKey(),
                                                                  textGenerationConnectionParameters.getMaxTokens(),
                                                                  textGenerationConnectionParameters.getTemperature(),
                                                                  textGenerationConnectionParameters.getTopP(),
                                                                  textGenerationConnectionParameters.getTimeout()),
                                                textGenerationConnectionParameters.getMcpSseServers());
  }
}
