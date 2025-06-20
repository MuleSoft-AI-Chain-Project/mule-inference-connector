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
import com.mulesoft.connectors.inference.internal.connection.provider.VisionModelConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.types.vertexai.VertexAIVisionConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.llmmodels.vertexai.providers.VertexAIVisionModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("vertexai-vision")
@DisplayName("Vertex AI")
public class VertexAIVisionConnectionProvider extends VisionModelConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(VertexAIVisionConnectionProvider.class);

  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(VertexAIVisionModelNameProvider.class)
  private String vertexAIModelName;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private TextGenerationConnectionParameters textGenerationConnectionParameters;

  @Override
  public VertexAIVisionConnection connect() throws ConnectionException {
    logger.debug("VertexAIVisionConnection connect ...");
    return new VertexAIVisionConnection(getHttpClient(), getObjectMapper(), new ParametersDTO(
                                                                                              vertexAIModelName,
                                                                                              textGenerationConnectionParameters
                                                                                                  .getApiKey(),
                                                                                              textGenerationConnectionParameters
                                                                                                  .getMaxTokens(),
                                                                                              textGenerationConnectionParameters
                                                                                                  .getTemperature(),
                                                                                              textGenerationConnectionParameters
                                                                                                  .getTopP(),
                                                                                              textGenerationConnectionParameters
                                                                                                  .getTimeout()));
  }
}
