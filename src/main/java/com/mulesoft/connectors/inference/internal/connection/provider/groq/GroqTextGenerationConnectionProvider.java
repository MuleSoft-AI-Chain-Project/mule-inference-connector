package com.mulesoft.connectors.inference.internal.connection.provider.groq;

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
import com.mulesoft.connectors.inference.internal.connection.types.groq.GroqTextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.llmmodels.groq.providers.GroqTextGenerationModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("groq")
@DisplayName("Groq")
public class GroqTextGenerationConnectionProvider extends TextGenerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(GroqTextGenerationConnectionProvider.class);

  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(GroqTextGenerationModelNameProvider.class)
  private String groqModelName;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private TextGenerationConnectionParameters textGenerationConnectionParameters;

  @Override
  public GroqTextGenerationConnection connect() throws ConnectionException {
    logger.debug("GroqTextGenerationConnection connect ...");
    return new GroqTextGenerationConnection(getHttpClient(), getObjectMapper(),
                                            new ParametersDTO(groqModelName,
                                                              textGenerationConnectionParameters.getApiKey(),
                                                              textGenerationConnectionParameters.getMaxTokens(),
                                                              textGenerationConnectionParameters.getTemperature(),
                                                              textGenerationConnectionParameters.getTopP(),
                                                              textGenerationConnectionParameters.getTimeout()),
                                            textGenerationConnectionParameters.getMcpSseServers());
  }
}
