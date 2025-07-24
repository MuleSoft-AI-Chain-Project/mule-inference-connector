package com.mulesoft.connectors.inference.internal.connection.provider.openaicompatible;

import static com.mulesoft.connectors.inference.internal.connection.types.openaicompatible.OpenAICompatibleTextGenerationConnection.OPENAI_COMPATIBLE_ENDPOINT;

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
import com.mulesoft.connectors.inference.internal.connection.types.openaicompatible.OpenAICompatibleTextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.llmmodels.openai.providers.OpenAITextGenerationModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("openai-compatible")
@DisplayName("OpenAI Compatible Endpoint")
public class OpenAICompatibleTextGenerationConnectionProvider extends TextGenerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(OpenAICompatibleTextGenerationConnectionProvider.class);

  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(OpenAITextGenerationModelNameProvider.class)
  @Optional(defaultValue = "gpt-4o-mini")
  private String openAICompatibleModelName;

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @Optional(defaultValue = OPENAI_COMPATIBLE_ENDPOINT)
  @Placement(order = 2)
  @DisplayName("[OpenAI Compatible] Base URL")
  private String openAICompatibleURL;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private TextGenerationConnectionParameters openAITextGenerationConnectionParameters;

  @Override
  public OpenAICompatibleTextGenerationConnection connect() throws ConnectionException {
    logger.debug("OpenAICompatibleTextGenerationConnection connect ...");
    return new OpenAICompatibleTextGenerationConnection(getHttpClient(), getObjectMapper(),
                                                        new ParametersDTO(openAICompatibleModelName,
                                                                          openAITextGenerationConnectionParameters.getApiKey(),
                                                                          openAITextGenerationConnectionParameters.getMaxTokens(),
                                                                          openAITextGenerationConnectionParameters
                                                                              .getTemperature(),
                                                                          openAITextGenerationConnectionParameters.getTopP(),
                                                                          openAITextGenerationConnectionParameters.getTimeout()),
                                                        openAICompatibleURL);
  }
}
