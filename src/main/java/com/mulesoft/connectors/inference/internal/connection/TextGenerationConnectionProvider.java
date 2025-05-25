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

public abstract class TextGenerationConnectionProvider extends BaseConnectionProvider
        implements CachedConnectionProvider<TextGenerationConnection>, Initialisable, Disposable {

    private final Logger logger = LoggerFactory.getLogger(TextGenerationConnectionProvider.class);

    @Override
    public ConnectionValidationResult validate(TextGenerationConnection connection) {

        logger.debug("Validating TextGenerationConnection ... ");
        try {
            var resp = connection.getTextGenerationService().executeChatAnswerPrompt(connection,"What is the capital of France?");
            var textResponse = this.getObjectMapper().readValue(resp.getOutput(), TextGenerationResponse.class);

            if (textResponse.response().contains("Paris")) {
                return ConnectionValidationResult.success();
            }
        } catch (IOException | TimeoutException e)
        {
            return ConnectionValidationResult.failure("Failed to validate TextGenerationConnection", e);
        }
        return ConnectionValidationResult.failure("Failed to validate TextGenerationConnection",null);
    }

    @Override
    public void disconnect(TextGenerationConnection baseConnection) {
        logger.debug("TextGenerationConnection disconnected ...");
    }
}
