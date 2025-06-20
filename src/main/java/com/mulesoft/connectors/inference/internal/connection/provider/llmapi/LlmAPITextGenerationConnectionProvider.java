package com.mulesoft.connectors.inference.internal.connection.provider.llmapi;

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
import com.mulesoft.connectors.inference.internal.connection.types.llmapi.LlmAPITextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.llmmodels.llamaapi.providers.LlmAPITextGenerationModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("llmapi")
@DisplayName("Llm API")
public class LlmAPITextGenerationConnectionProvider extends TextGenerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(LlmAPITextGenerationConnectionProvider.class);

  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(LlmAPITextGenerationModelNameProvider.class)
  private String llmApiModelName;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private TextGenerationConnectionParameters textGenerationConnectionParameters;

  @Override
  public LlmAPITextGenerationConnection connect() throws ConnectionException {
    logger.debug("LlmAPITextGenerationConnection connect ...");
    return new LlmAPITextGenerationConnection(getHttpClient(), getObjectMapper(),
                                              new ParametersDTO(llmApiModelName,
                                                                textGenerationConnectionParameters.getApiKey(),
                                                                textGenerationConnectionParameters.getMaxTokens(),
                                                                textGenerationConnectionParameters.getTemperature(),
                                                                textGenerationConnectionParameters.getTopP(),
                                                                textGenerationConnectionParameters.getTimeout()),
                                              textGenerationConnectionParameters.getMcpSseServers());
  }
}
