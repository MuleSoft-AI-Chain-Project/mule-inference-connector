package com.mulesoft.connectors.inference.internal.connection.provider.huggingface;

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
import com.mulesoft.connectors.inference.internal.connection.types.huggingface.HuggingFaceVisionConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.llmmodels.huggingface.providers.HuggingFaceVisionModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("hugging-face-vision")
@DisplayName("Hugging Face")
public class HuggingFaceVisionConnectionProvider extends VisionModelConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(HuggingFaceVisionConnectionProvider.class);

  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(HuggingFaceVisionModelNameProvider.class)
  private String huggingFaceModelName;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private TextGenerationConnectionParameters textGenerationConnectionParameters;

  @Override
  public HuggingFaceVisionConnection connect() throws ConnectionException {
    logger.debug("HuggingFaceVisionConnection connect ...");
    return new HuggingFaceVisionConnection(getHttpClient(), getObjectMapper(), new ParametersDTO(
                                                                                                 huggingFaceModelName,
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
