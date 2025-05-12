package com.mulesoft.connectors.internal.connection.openai.providers;

import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.internal.connection.TextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.openai.OpenAITextGenerationConnectionParameters;
import com.mulesoft.connectors.internal.connection.openai.OpenAITextGenerationConnection;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;

@Alias("openai")
@DisplayName("OpenAI")
public class OpenAITextGenerationConnectionProvider extends TextGenerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(OpenAITextGenerationConnectionProvider.class);

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private OpenAITextGenerationConnectionParameters openAITextGenerationConnectionParameters;

  @Override
  public OpenAITextGenerationConnection connect() throws ConnectionException {
      logger.debug("OpenAITextGenerationConnection connect ...");
      try {
          return new OpenAITextGenerationConnection(httpClient, openAITextGenerationConnectionParameters.getOpenAIModelName(),
                  openAITextGenerationConnectionParameters.getApiKey(),
                  openAITextGenerationConnectionParameters.getTemperature(), openAITextGenerationConnectionParameters.getTopP(),
                  openAITextGenerationConnectionParameters.getMaxTokens(), openAITextGenerationConnectionParameters.getMcpSseServers(),
                  openAITextGenerationConnectionParameters.getTimeout());
      } catch (MalformedURLException e) {
          throw new ConnectionException("Invalid Open Compatible URL",e);
      }
  }

  @Override
  public void disconnect(TextGenerationConnection baseConnection) {
    logger.debug(" OpenAITextGenerationConnection disconnected ...");
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
