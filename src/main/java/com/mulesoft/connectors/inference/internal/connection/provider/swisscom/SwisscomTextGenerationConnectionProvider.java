package com.mulesoft.connectors.inference.internal.connection.provider.swisscom;

import static com.mulesoft.connectors.inference.internal.connection.types.swisscom.SwisscomTextGenerationConnection.SWISSCOM_URL;

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
import com.mulesoft.connectors.inference.internal.connection.types.swisscom.SwisscomTextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.llmmodels.swisscom.providers.SwisscomTextGenerationModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("swisscom")
@DisplayName("Swisscom")
public class SwisscomTextGenerationConnectionProvider extends TextGenerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(SwisscomTextGenerationConnectionProvider.class);

  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(SwisscomTextGenerationModelNameProvider.class)
  @Optional(defaultValue = "meta/llama-3.3-70b-instruct")
  private String swisscomModelName;

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @Optional(defaultValue = SWISSCOM_URL)
  @Placement(order = 2)
  @DisplayName("[Swisscom] Base URL")
  private String swisscomURL;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private TextGenerationConnectionParameters swisscomTextGenerationConnectionParameters;

  @Override
  public SwisscomTextGenerationConnection connect() throws ConnectionException {
    logger.debug("swisscomTextGenerationConnection connect ...");
    return new SwisscomTextGenerationConnection(getHttpClient(), getObjectMapper(),
                                                new ParametersDTO(swisscomModelName,
                                                                  swisscomTextGenerationConnectionParameters.getApiKey(),
                                                                  swisscomTextGenerationConnectionParameters.getMaxTokens(),
                                                                  swisscomTextGenerationConnectionParameters.getTemperature(),
                                                                  swisscomTextGenerationConnectionParameters.getTopP(),
                                                                  swisscomTextGenerationConnectionParameters.getTimeout(),
                                                                  swisscomTextGenerationConnectionParameters.getCustomHeaders()),
                                                swisscomURL);
  }
}
