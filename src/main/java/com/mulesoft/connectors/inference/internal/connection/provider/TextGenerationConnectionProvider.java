package com.mulesoft.connectors.inference.internal.connection.provider;

import org.mule.runtime.api.connection.CachedConnectionProvider;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.lifecycle.Disposable;
import org.mule.runtime.api.lifecycle.Initialisable;
import org.mule.runtime.extension.api.exception.ModuleException;

import com.mulesoft.connectors.inference.api.response.TextGenerationResponse;
import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.error.InferenceErrorType;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TextGenerationConnectionProvider extends BaseConnectionProvider
    implements CachedConnectionProvider<TextGenerationConnection>, Initialisable, Disposable {

  private static final Logger logger = LoggerFactory.getLogger(TextGenerationConnectionProvider.class);

  @Override
  public ConnectionValidationResult validate(TextGenerationConnection connection) {

    logger.debug("Validating TextGenerationConnection ... ");
    try {
      var resp = connection.getTextGenerationService().executeChatAnswerPrompt(connection, "What is the capital of France?");
      var textResponse = this.getObjectMapper().readValue(resp.getOutput(), TextGenerationResponse.class);

      if (textResponse.response().contains("Paris")) {
        return ConnectionValidationResult.success();
      }
    } catch (IOException | TimeoutException | ModuleException e) {
      return ConnectionValidationResult.failure("Failed to validate TextGenerationConnection",
                                                new ModuleException("Error validating connection.",
                                                                    InferenceErrorType.INVALID_CONNECTION, e));
    }
    return ConnectionValidationResult.failure("Failed to validate TextGenerationConnection",
                                              new ModuleException("Error validating connection.",
                                                                  InferenceErrorType.INVALID_CONNECTION));
  }

  @Override
  public void disconnect(TextGenerationConnection baseConnection) {
    logger.debug("TextGenerationConnection disconnected ...");
  }
}
