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
import com.mulesoft.connectors.inference.internal.connection.provider.TextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.types.huggingface.HuggingFaceTextGenerationConnection;
import com.mulesoft.connectors.inference.internal.llmmodels.huggingface.providers.HuggingFaceTextGenerationModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("hugging-face")
@DisplayName("Hugging Face")
public class HuggingFaceTextGenerationConnectionProvider extends TextGenerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(HuggingFaceTextGenerationConnectionProvider.class);

  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(HuggingFaceTextGenerationModelNameProvider.class)
  private String huggingFaceModelName;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private TextGenerationConnectionParameters textGenerationConnectionParameters;

  @Override
  public HuggingFaceTextGenerationConnection connect() throws ConnectionException {
    logger.debug("HuggingFaceTextGenerationConnection connect ...");
    return new HuggingFaceTextGenerationConnection(getHttpClient(), getObjectMapper(), huggingFaceModelName,
                                                   textGenerationConnectionParameters.getApiKey(),
                                                   textGenerationConnectionParameters.getTemperature(),
                                                   textGenerationConnectionParameters.getTopP(),
                                                   textGenerationConnectionParameters.getMaxTokens(),
                                                   textGenerationConnectionParameters.getMcpSseServers(),
                                                   textGenerationConnectionParameters.getTimeout());
  }
}
