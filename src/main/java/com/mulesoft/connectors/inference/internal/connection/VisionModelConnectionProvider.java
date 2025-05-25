package com.mulesoft.connectors.inference.internal.connection;

import com.mulesoft.connectors.inference.api.response.TextGenerationResponse;
import org.mule.runtime.api.connection.CachedConnectionProvider;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.lifecycle.Disposable;
import org.mule.runtime.api.lifecycle.Initialisable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public abstract class VisionModelConnectionProvider extends BaseConnectionProvider
        implements CachedConnectionProvider<VisionModelConnection>, Initialisable, Disposable {

    private static final Logger logger = LoggerFactory.getLogger(VisionModelConnectionProvider.class);

    @Override
    public ConnectionValidationResult validate(VisionModelConnection connection) {

        logger.debug("Validating VisionModelConnection... ");
        try {
            var resp =  connection.getVisionModelService().readImage(connection,"What do you see?",
                    "https://tripfixers.com/wp-content/uploads/2019/11/eiffel-tower-with-snow.jpeg");
            var textResponse = this.getObjectMapper().readValue(resp.getOutput(), TextGenerationResponse.class);

            if (textResponse.response().contains("Eiffel Tower")) {
                return ConnectionValidationResult.success();
            }
        } catch (IOException | TimeoutException e)
        {
            return ConnectionValidationResult.failure("Failed to validate VisionModelConnection", e);
        }
        return ConnectionValidationResult.failure("Failed to validate VisionModelConnection",null);
    }

    @Override
    public void disconnect(VisionModelConnection connection) {
        logger.debug(" VisionModelConnection disconnected ...");
    }
}
