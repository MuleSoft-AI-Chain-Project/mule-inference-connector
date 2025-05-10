package com.mulesoft.connectors.internal.connection.mistralai;

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

@Alias("mistralai")
@DisplayName("MistralAI")
public class MistralAIConnectionProvider extends TextGenerationConnectionProvider {

  private static final Logger logger = LoggerFactory.getLogger(MistralAIConnectionProvider.class);

  public static final String OPENAI_COMPATIBLE_ENDPOINT = "https://server.endpoint.com";

  @ParameterGroup(name = Placement.CONNECTION_TAB)
  private MistralAIConnectionParameters mistralAIConnectionParameters;

  @Override
  public MistralAITextGenerationConnection connect() throws ConnectionException {
      try {
          return new MistralAITextGenerationConnection(httpClient, mistralAIConnectionParameters.getMistralAIModelName(),
                  mistralAIConnectionParameters.getApiKey(),
                  mistralAIConnectionParameters.getTemperature(), mistralAIConnectionParameters.getTopP(),
                  mistralAIConnectionParameters.getMaxTokens(), mistralAIConnectionParameters.getMcpSseServers(),
                  mistralAIConnectionParameters.getTimeout());
      } catch (MalformedURLException e) {
          throw new ConnectionException("Invalid Open Compatible URL",e);
      }
  }

  @Override
  public void disconnect(TextGenerationConnection textGenerationConnection) {
    logger.debug("Disconnected ...");
  }

  @Override
  public ConnectionValidationResult validate(TextGenerationConnection textGenerationConnection) {

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
