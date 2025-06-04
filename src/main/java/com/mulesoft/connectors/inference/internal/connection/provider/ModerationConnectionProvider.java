package com.mulesoft.connectors.inference.internal.connection.provider;

import com.mulesoft.connectors.inference.api.response.ModerationResponse;
import com.mulesoft.connectors.inference.internal.connection.types.ModerationConnection;
import org.apache.commons.io.IOUtils;
import org.mule.runtime.api.connection.CachedConnectionProvider;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.lifecycle.Disposable;
import org.mule.runtime.api.lifecycle.Initialisable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;

public abstract class ModerationConnectionProvider extends BaseConnectionProvider
        implements CachedConnectionProvider<ModerationConnection>, Initialisable, Disposable {

    private static final Logger logger = LoggerFactory.getLogger(ModerationConnectionProvider.class);

    @Override
    public ConnectionValidationResult validate(ModerationConnection connection) {

        logger.debug("Validating ModerationConnection... ");
        try {
            var resp =  connection.getModerationService().executeTextModeration(connection,
                    IOUtils.toInputStream("You are fat", Charset.defaultCharset()));
            var moderationResponse = this.getObjectMapper().readValue(resp.getOutput(), ModerationResponse.class);

            if (moderationResponse.flagged()) {
                return ConnectionValidationResult.success();
            }
        } catch (IOException | TimeoutException e)
        {
            return ConnectionValidationResult.failure("Failed to validate ModerationConnection", e);
        }
        return ConnectionValidationResult.failure("Failed to validate ModerationConnection",null);
    }

    @Override
    public void disconnect(ModerationConnection connection) {
        logger.debug(" ModerationConnection disconnected ...");
    }
}
