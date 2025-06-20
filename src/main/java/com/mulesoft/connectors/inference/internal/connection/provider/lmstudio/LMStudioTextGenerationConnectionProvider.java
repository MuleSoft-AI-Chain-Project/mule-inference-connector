package com.mulesoft.connectors.inference.internal.connection.provider.lmstudio;

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
import com.mulesoft.connectors.inference.internal.connection.types.lmstudio.LMStudioTextGenerationConnection;
import com.mulesoft.connectors.inference.internal.constants.InferenceConstants;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.llmmodels.lmstudio.providers.LMStudioTextGenerationModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("lmstudio")
@DisplayName("LM Studio")
public class LMStudioTextGenerationConnectionProvider extends TextGenerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(LMStudioTextGenerationConnectionProvider.class);

  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(LMStudioTextGenerationModelNameProvider.class)
  private String lmStudioModelName;

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @Optional(defaultValue = InferenceConstants.LMSTUDIO_URL)
  @Placement(order = 2)
  @DisplayName("[LM Studio] Base URL")
  private String lmStudioBaseURL;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private TextGenerationConnectionParameters textGenerationConnectionParameters;

  @Override
  public LMStudioTextGenerationConnection connect() throws ConnectionException {
    logger.debug("LMStudioTextGenerationConnection connect ...");
    return new LMStudioTextGenerationConnection(getHttpClient(), getObjectMapper(),
                                                new ParametersDTO(lmStudioModelName,
                                                                  textGenerationConnectionParameters.getApiKey(),
                                                                  textGenerationConnectionParameters.getMaxTokens(),
                                                                  textGenerationConnectionParameters.getTemperature(),
                                                                  textGenerationConnectionParameters.getTopP(),
                                                                  textGenerationConnectionParameters.getTimeout()),
                                                lmStudioBaseURL,
                                                textGenerationConnectionParameters.getMcpSseServers());
  }
}
