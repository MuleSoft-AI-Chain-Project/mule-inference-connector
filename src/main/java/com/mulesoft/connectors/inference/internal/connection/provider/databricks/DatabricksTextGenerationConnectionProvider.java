package com.mulesoft.connectors.inference.internal.connection.provider.databricks;

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
import com.mulesoft.connectors.inference.internal.connection.types.databricks.DatabricksTextGenerationConnection;
import com.mulesoft.connectors.inference.internal.llmmodels.databricks.providers.DatabricksTextGenerationModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("databricks")
@DisplayName("Databricks")
public class DatabricksTextGenerationConnectionProvider extends TextGenerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(DatabricksTextGenerationConnectionProvider.class);

  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(DatabricksTextGenerationModelNameProvider.class)
  private String databricksModelName;

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @Optional(defaultValue = "https://<instance>.cloud.databricks.com/")
  @DisplayName("[Databricks] Model URL")
  private String dataBricksModelUrl;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private TextGenerationConnectionParameters textGenerationConnectionParameters;

  @Override
  public DatabricksTextGenerationConnection connect() throws ConnectionException {
    logger.debug("DatabricksTextGenerationConnection connect ...");
    return new DatabricksTextGenerationConnection(getHttpClient(), getObjectMapper(), databricksModelName, dataBricksModelUrl,
                                                  textGenerationConnectionParameters.getApiKey(),
                                                  textGenerationConnectionParameters.getTemperature(),
                                                  textGenerationConnectionParameters.getTopP(),
                                                  textGenerationConnectionParameters.getMaxTokens(),
                                                  textGenerationConnectionParameters.getMcpSseServers(),
                                                  textGenerationConnectionParameters.getTimeout());
  }
}
