package com.mulesoft.connectors.inference.internal.operation;

import com.mulesoft.connectors.inference.internal.connection.types.ModerationConnection;
import com.mulesoft.connectors.inference.internal.error.InferenceErrorType;
import com.mulesoft.connectors.inference.internal.error.provider.ModerationErrorTypeProvider;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.error.Throws;
import org.mule.runtime.extension.api.annotation.metadata.fixed.OutputJsonType;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.Content;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.mule.runtime.extension.api.exception.ModuleException;
import org.mule.runtime.extension.api.runtime.operation.Result;

import java.io.InputStream;

import static org.mule.runtime.extension.api.annotation.param.MediaType.APPLICATION_JSON;

@Throws(ModerationErrorTypeProvider.class)
public class ModerationOperations {

    @MediaType(value = APPLICATION_JSON, strict = false)
    @Alias("Toxicity-Detection-Text")
    @DisplayName("[Toxicity] Detection by Text")
    @OutputJsonType(schema = "api/response/ResponseModeration.json")
    @Summary("Detects toxic input by text and classifies it into categories.")
    public Result<InputStream, Void> textModeration(
            @Connection ModerationConnection connection,
            @Content(primary = true) @Summary("Text to moderate. Can be a single string or an array of strings") InputStream text) throws ModuleException {
        try {
            return connection.getService().getModerationServiceInstance().executeTextModeration(connection, text);

        } catch (ModuleException e) {
            throw e;
        } catch (Exception e) {
            throw new ModuleException("Failed to process moderation request payload", InferenceErrorType.TOXICITY_DETECTION_OPERATION_FAILURE, e);
        }
    }
}