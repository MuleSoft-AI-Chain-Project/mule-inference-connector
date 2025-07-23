package com.mulesoft.connectors.inference.internal.connection.provider.heroku;

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
import com.mulesoft.connectors.inference.internal.connection.types.heroku.HerokuTextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.llmmodels.heroku.providers.HerokuTextGenerationModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("heroku")
@DisplayName("Heroku AI")
public class HerokuTextGenerationConnectionProvider extends TextGenerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(HerokuTextGenerationConnectionProvider.class);

  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(HerokuTextGenerationModelNameProvider.class)
  @Optional(defaultValue = "claude-3-7-sonnet")
  private String herokuInferenceModel;

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @Placement(order = 2)
  @Optional(defaultValue = "https://us.inference.heroku.com")
  @DisplayName("Heroku Inference URL")
  private String herokuInferenceUrl;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private TextGenerationConnectionParameters herokuTextGenerationConnectionParameters;

  @Override
  public HerokuTextGenerationConnection connect() throws ConnectionException {
    logger.debug("HerokuTextGenerationConnection connect ...");
    return new HerokuTextGenerationConnection(getHttpClient(), getObjectMapper(),
                                              new ParametersDTO(herokuInferenceModel,
                                                                herokuTextGenerationConnectionParameters.getApiKey(),
                                                                herokuTextGenerationConnectionParameters.getMaxTokens(),
                                                                herokuTextGenerationConnectionParameters
                                                                    .getTemperature(),
                                                                herokuTextGenerationConnectionParameters.getTopP(),
                                                                herokuTextGenerationConnectionParameters.getTimeout()),
                                              herokuInferenceUrl);
  }
}
