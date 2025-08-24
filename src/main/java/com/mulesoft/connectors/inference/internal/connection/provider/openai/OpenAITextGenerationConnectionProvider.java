package com.mulesoft.connectors.inference.internal.connection.provider.openai;

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
import com.mulesoft.connectors.inference.internal.connection.types.openai.OpenAITextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.llmmodels.openai.providers.OpenAITextGenerationModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("openai")
@DisplayName("OpenAI")
public class OpenAITextGenerationConnectionProvider extends TextGenerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(OpenAITextGenerationConnectionProvider.class);

  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(OpenAITextGenerationModelNameProvider.class)
  @Optional(defaultValue = "gpt-4o-mini")
  private String openAIModelName;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private TextGenerationConnectionParameters openAITextGenerationConnectionParameters;

  @Override
  public OpenAITextGenerationConnection connect() throws ConnectionException {
    logger.debug("OpenAITextGenerationConnection connect ...");
    return new OpenAITextGenerationConnection(getHttpClient(), getObjectMapper(),
                                              new ParametersDTO(openAIModelName,
                                                                openAITextGenerationConnectionParameters.getApiKey(),
                                                                openAITextGenerationConnectionParameters.getMaxTokens(),
                                                                openAITextGenerationConnectionParameters.getTemperature(),
                                                                openAITextGenerationConnectionParameters.getTopP(),
                                                                openAITextGenerationConnectionParameters.getTimeout(),
                                                                openAITextGenerationConnectionParameters.getCustomHeaders()));
  }
}
