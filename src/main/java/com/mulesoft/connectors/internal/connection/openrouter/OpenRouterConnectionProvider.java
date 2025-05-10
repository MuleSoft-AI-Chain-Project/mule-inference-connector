package com.mulesoft.connectors.internal.connection.openrouter;

import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.internal.connection.TextGenerationConnectionProvider;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;

@Alias("openrouter")
@DisplayName("OpenRouter")
public class OpenRouterConnectionProvider extends TextGenerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(OpenRouterConnectionProvider.class);

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private OpenRouterConnectionParameters openRouterConnectionParameters;

  @Override
  public OpenRouterTextGenerationConnection connect() throws ConnectionException {
      logger.debug("OpenRouterTextGenerationConnection connect ...");
      try {
          return new OpenRouterTextGenerationConnection(httpClient, openRouterConnectionParameters.getOpenRouterModelName(),
                  openRouterConnectionParameters.getApiKey(),
                  openRouterConnectionParameters.getTemperature(), openRouterConnectionParameters.getTopP(),
                  openRouterConnectionParameters.getMaxTokens(), openRouterConnectionParameters.getMcpSseServers(),
                  openRouterConnectionParameters.getTimeout());
      } catch (MalformedURLException e) {
          throw new ConnectionException("Invalid Open Compatible URL",e);
      }
  }

  @Override
  public void disconnect(TextGenerationConnection baseConnection) {
    logger.debug(" OpenRouterTextGenerationConnection disconnected ...");
  }

  @Override
  public ConnectionValidationResult validate(TextGenerationConnection baseConnection) {

    logger.debug("Validating connection... ");
    try {
      //TODO implement proper call to validate connection is valid
      // if (textGenerationConnection.isValid()) {
      return ConnectionValidationResult.success();
     /* } else {
        return ConnectionValidationResult.failure("Failed to validate connection to PGVector", null);
      }*/
    } catch (Exception e) {
      return ConnectionValidationResult.failure("Failed to validate connection to PGVector", e);
    }
  }
}
