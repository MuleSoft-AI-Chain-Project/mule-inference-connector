package com.mulesoft.connectors.internal.connection.openai;

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

@Alias("openai")
@DisplayName("OpenAI")
public class OpenAIConnectionProvider extends TextGenerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(OpenAIConnectionProvider.class);

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private OpenAIConnectionParameters openAIConnectionParameters;

  @Override
  public OpenAITextGenerationConnection connect() throws ConnectionException {
      logger.debug("OpenAITextGenerationConnection connect ...");
      try {
          return new OpenAITextGenerationConnection(httpClient,openAIConnectionParameters.getOpenAIModelName(),
                  openAIConnectionParameters.getApiKey(),
                  openAIConnectionParameters.getTemperature(),openAIConnectionParameters.getTopP(),
                  openAIConnectionParameters.getMaxTokens(),openAIConnectionParameters.getMcpSseServers(),
                  openAIConnectionParameters.getTimeout());
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
