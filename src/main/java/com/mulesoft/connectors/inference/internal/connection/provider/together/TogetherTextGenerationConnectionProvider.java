package com.mulesoft.connectors.inference.internal.connection.provider.together;

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
import com.mulesoft.connectors.inference.internal.connection.types.together.TogetherTextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.llmmodels.together.providers.TogetherTextGenerationModelNameProvider;

@Alias("together")
@DisplayName("Together")
public class TogetherTextGenerationConnectionProvider extends TextGenerationConnectionProvider {

  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(TogetherTextGenerationModelNameProvider.class)
  private String togetherModelName;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private TextGenerationConnectionParameters textGenerationConnectionParameters;

  @Override
  public TogetherTextGenerationConnection connect() throws ConnectionException {
    return new TogetherTextGenerationConnection(getHttpClient(), getObjectMapper(),
                                                new ParametersDTO(togetherModelName,
                                                                  textGenerationConnectionParameters.getApiKey(),
                                                                  textGenerationConnectionParameters.getMaxTokens(),
                                                                  textGenerationConnectionParameters.getTemperature(),
                                                                  textGenerationConnectionParameters.getTopP(),
                                                                  textGenerationConnectionParameters.getTimeout()),
                                                textGenerationConnectionParameters.getMcpSseServers());
  }
}
