package com.mulesoft.connectors.inference.internal.connection.provider.vertexai;

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
import com.mulesoft.connectors.inference.internal.connection.types.vertexai.VertexAIExpressTextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.llmmodels.vertexai.providers.VertexAIExpressTextGenerationModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("vertexai-express")
@DisplayName("Vertex AI Express")
public class VertexAIExpressTextGenerationConnectionProvider extends TextGenerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(VertexAIExpressTextGenerationConnectionProvider.class);

  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(VertexAIExpressTextGenerationModelNameProvider.class)
  private String vertexAIExpressModelName;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private TextGenerationConnectionParameters textGenerationConnectionParameters;

  @Override
  public VertexAIExpressTextGenerationConnection connect() throws ConnectionException {
    logger.debug("VertexAIExpressTextGenerationConnection connect ...");
    return new VertexAIExpressTextGenerationConnection(getHttpClient(), getObjectMapper(),
                                                       new ParametersDTO(vertexAIExpressModelName,
                                                                         textGenerationConnectionParameters.getApiKey(),
                                                                         textGenerationConnectionParameters.getMaxTokens(),
                                                                         textGenerationConnectionParameters.getTemperature(),
                                                                         textGenerationConnectionParameters.getTopP(),
                                                                         textGenerationConnectionParameters.getTimeout()),
                                                       textGenerationConnectionParameters.getMcpSseServers());
  }
}
