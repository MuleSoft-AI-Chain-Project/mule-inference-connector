package com.mulesoft.connectors.inference.internal.connection.provider.databricks;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Example;
import org.mule.runtime.extension.api.annotation.param.display.Placement;

import com.mulesoft.connectors.inference.internal.connection.parameters.TextGenerationConnectionParameters;
import com.mulesoft.connectors.inference.internal.connection.provider.TextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.types.databricks.DatabricksTextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("databricks")
@DisplayName("Databricks")
public class DatabricksTextGenerationConnectionProvider extends TextGenerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(DatabricksTextGenerationConnectionProvider.class);

  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @Example(value = "databricks-meta-llama-3-3-70b-instruct")
  @DisplayName("[Databricks] Serving Endpoint Name")
  private String servingEndpointName;

  @Parameter
  @Placement(order = 2)
  @Expression(ExpressionSupport.SUPPORTED)
  @Example(value = "https://<instance>.cloud.databricks.com/")
  @DisplayName("[Databricks] Serving Endpoint URL host")
  private String servingEndpointUrlHost;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private TextGenerationConnectionParameters textGenerationConnectionParameters;

  @Override
  public DatabricksTextGenerationConnection connect() throws ConnectionException {
    logger.debug("DatabricksTextGenerationConnection connect ...");
    return new DatabricksTextGenerationConnection(getHttpClient(), getObjectMapper(),
                                                  new ParametersDTO(servingEndpointName,
                                                                    textGenerationConnectionParameters.getApiKey(),
                                                                    textGenerationConnectionParameters.getMaxTokens(),
                                                                    textGenerationConnectionParameters.getTemperature(),
                                                                    textGenerationConnectionParameters.getTopP(),
                                                                    textGenerationConnectionParameters.getTimeout()),
                                                  servingEndpointUrlHost,
                                                  textGenerationConnectionParameters.getMcpSseServers());
  }
}
