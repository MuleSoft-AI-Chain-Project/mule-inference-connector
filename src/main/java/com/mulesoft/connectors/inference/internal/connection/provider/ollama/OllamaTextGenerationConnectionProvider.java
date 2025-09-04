package com.mulesoft.connectors.inference.internal.connection.provider.ollama;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.mule.runtime.extension.api.annotation.values.OfValues;

import com.mulesoft.connectors.inference.internal.connection.parameters.TextGenerationConnectionParameters;
import com.mulesoft.connectors.inference.internal.connection.provider.TextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.types.ollama.OllamaTextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.llmmodels.ollama.providers.OllamaTextGenerationModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("ollama")
@DisplayName("Ollama")
public class OllamaTextGenerationConnectionProvider extends TextGenerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(OllamaTextGenerationConnectionProvider.class);

  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(OllamaTextGenerationModelNameProvider.class)
  @Summary("This inference model is currently supported as a beta feature. Please refer to the product documentation")
  private String ollamaModelName;

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @Optional(defaultValue = "http://localhost:11434/api")
  @Placement(order = 2)
  @DisplayName("[Ollama] Base URL")
  private String ollamaUrl;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private TextGenerationConnectionParameters textGenerationConnectionParameters;

  @Override
  public OllamaTextGenerationConnection connect() throws ConnectionException {
    logger.debug("OllamaTextGenerationConnection connect ...");
    return new OllamaTextGenerationConnection(getHttpClient(), getObjectMapper(),
                                              new ParametersDTO(ollamaModelName,
                                                                textGenerationConnectionParameters.getApiKey(),
                                                                textGenerationConnectionParameters.getMaxTokens(),
                                                                textGenerationConnectionParameters.getTemperature(),
                                                                textGenerationConnectionParameters.getTopP(),
                                                                textGenerationConnectionParameters.getTimeout(),
                                                                textGenerationConnectionParameters.getCustomHeaders()),
                                              ollamaUrl);
  }
}
