package com.mulesoft.connectors.inference.internal.connection.provider;

import org.mule.runtime.api.connection.CachedConnectionProvider;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.lifecycle.Disposable;
import org.mule.runtime.api.lifecycle.Initialisable;
import org.mule.runtime.extension.api.exception.ModuleException;

import com.mulesoft.connectors.inference.api.response.TextGenerationResponse;
import com.mulesoft.connectors.inference.internal.connection.types.VisionModelConnection;
import com.mulesoft.connectors.inference.internal.error.InferenceErrorType;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class VisionModelConnectionProvider extends BaseConnectionProvider
    implements CachedConnectionProvider<VisionModelConnection>, Initialisable, Disposable {

  private static final Logger logger = LoggerFactory.getLogger(VisionModelConnectionProvider.class);

  @Override
  public ConnectionValidationResult validate(VisionModelConnection connection) {

    logger.debug("Validating VisionModelConnection... ");
    try {
      var resp = connection.getVisionModelService().readImage(connection, "What do you see?",
                                                              "https://tripfixers.com/wp-content/uploads/2019/11/eiffel-tower-with-snow.jpeg");
      var textResponse = this.getObjectMapper().readValue(resp.getOutput(), TextGenerationResponse.class);

      if (textResponse.response().contains("Eiffel Tower")) {
        return ConnectionValidationResult.success();
      }
    } catch (IOException | TimeoutException | ModuleException e) {
      return ConnectionValidationResult.failure("Failed to validate VisionModelConnection",
                                                new ModuleException("Error validating connection.",
                                                                    InferenceErrorType.INVALID_CONNECTION, e));
    }
    return ConnectionValidationResult.failure("Failed to validate VisionModelConnection",
                                              new ModuleException("Error validating connection.",
                                                                  InferenceErrorType.INVALID_CONNECTION));
  }

  @Override
  public void disconnect(VisionModelConnection connection) {
    logger.debug(" VisionModelConnection disconnected ...");
  }
}
