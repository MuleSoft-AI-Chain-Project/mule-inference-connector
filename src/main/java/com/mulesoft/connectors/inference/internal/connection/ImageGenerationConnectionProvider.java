package com.mulesoft.connectors.inference.internal.connection;

import com.mulesoft.connectors.inference.api.response.ImageGenerationResponse;
import org.apache.commons.lang3.StringUtils;
import org.mule.runtime.api.connection.CachedConnectionProvider;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.lifecycle.Disposable;
import org.mule.runtime.api.lifecycle.Initialisable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public abstract class ImageGenerationConnectionProvider extends BaseConnectionProvider
        implements CachedConnectionProvider<ImageGenerationConnection>, Initialisable, Disposable {

    private final Logger logger = LoggerFactory.getLogger(ImageGenerationConnectionProvider.class);

    @Override
    public ConnectionValidationResult validate(ImageGenerationConnection connection) {

        logger.debug("Validating ImageGenerationConnection... ");
        try {
            var resp =  connection.getImageGenerationService().executeGenerateImage(connection,
                    "Generate a picture of a penguin dancing.");
            var imageGenerationResponse = this.getObjectMapper().readValue(resp.getOutput(), ImageGenerationResponse.class);

            if (StringUtils.isNotBlank(imageGenerationResponse.response())) {
                return ConnectionValidationResult.success();
            }
        } catch (IOException | TimeoutException e)
        {
            return ConnectionValidationResult.failure("Failed to validate ImageGenerationConnection", e);
        }
        return ConnectionValidationResult.failure("Failed to validate ImageGenerationConnection",null);
    }

    @Override
    public void disconnect(ImageGenerationConnection imageGenerationConnection) {
        logger.debug(" ImageGenerationConnection disconnected ...");
    }
}
