package com.mulesoft.connectors.inference.internal.connection.provider.github;

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
import com.mulesoft.connectors.inference.internal.connection.types.github.GithubTextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.ParametersDTO;
import com.mulesoft.connectors.inference.internal.llmmodels.github.providers.GithubTextGenerationModelNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("github")
@DisplayName("GitHub")
public class GithubTextGenerationConnectionProvider extends TextGenerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(GithubTextGenerationConnectionProvider.class);

  @Parameter
  @Placement(order = 1)
  @Expression(ExpressionSupport.SUPPORTED)
  @OfValues(GithubTextGenerationModelNameProvider.class)
  private String gitHubModelName;

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private TextGenerationConnectionParameters textGenerationConnectionParameters;

  @Override
  public GithubTextGenerationConnection connect() throws ConnectionException {
    logger.debug("GitHubTextGenerationConnection connect ...");
    return new GithubTextGenerationConnection(getHttpClient(), getObjectMapper(),
                                              new ParametersDTO(gitHubModelName,
                                                                textGenerationConnectionParameters.getApiKey(),
                                                                textGenerationConnectionParameters.getMaxTokens(),
                                                                textGenerationConnectionParameters.getTemperature(),
                                                                textGenerationConnectionParameters.getTopP(),
                                                                textGenerationConnectionParameters.getTimeout()),
                                              textGenerationConnectionParameters.getMcpSseServers());
  }
}
