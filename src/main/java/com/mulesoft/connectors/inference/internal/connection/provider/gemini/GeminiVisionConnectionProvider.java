package com.mulesoft.connectors.inference.internal.connection.provider.gemini;

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
import com.mulesoft.connectors.inference.internal.connection.types.gemini.GeminiVisionConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.llmmodels.gemini.providers.GeminiVisionModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("gemini-vision")
@DisplayName("Gemini")
public class GeminiVisionConnectionProvider extends VisionModelConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(GeminiVisionConnectionProvider.class);

  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(GeminiVisionModelNameProvider.class)
  private String geminiModelName;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private TextGenerationConnectionParameters textGenerationConnectionParameters;

  @Override
  public GeminiVisionConnection connect() throws ConnectionException {
    logger.debug("GeminiVisionConnection connect ...");
    return new GeminiVisionConnection(getHttpClient(), getObjectMapper(), new ParametersDTO(
                                                                                            geminiModelName,
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
