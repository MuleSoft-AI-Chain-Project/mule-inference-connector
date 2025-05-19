package com.mulesoft.connectors.inference.internal.operations;

import com.mulesoft.connectors.inference.internal.api.delegate.Moderation;
import com.mulesoft.connectors.inference.api.metadata.LLMResponseAttributes;
import com.mulesoft.connectors.inference.internal.connection.BaseConnection;
import com.mulesoft.connectors.inference.internal.exception.InferenceErrorType;
import com.mulesoft.connectors.inference.internal.utils.ConnectionUtils;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.metadata.fixed.OutputJsonType;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.Content;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.mule.runtime.extension.api.exception.ModuleException;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.URL;

import static org.mule.runtime.extension.api.annotation.param.MediaType.APPLICATION_JSON;

public class ModerationOperations {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModerationOperations.class);
    private static final String ERROR_MSG_FORMAT = "%s moderation error";

    @MediaType(value = APPLICATION_JSON, strict = false)
    @Alias("Toxicity-Detection-Text")
    @DisplayName("[Toxicity] Detection by Text")
    @OutputJsonType(schema = "api/response/ResponseModeration.json")
    @Summary("Detects toxic input by text and classifies it into categories.")
    public Result<InputStream, LLMResponseAttributes> textModeration(
            @Connection BaseConnection connection,
            @Content(primary = true) @Summary("Text to moderate. Can be a single string or an array of strings") InputStream text) throws ModuleException {
        try {
            Moderation moderation = new Moderation(connection);
            String payload = moderation.getRequestPayload(text);
            LOGGER.debug("Moderation payload that will be sent to the LLM {}", payload);
            URL moderationURL = new URL(connection.getApiURL());

            String response =  ConnectionUtils.executeREST(moderationURL, connection, payload);
            LOGGER.debug("Moderation service - response from LLM: {}", response);
            return moderation.processResponse(response);

        } catch (Exception e) {
            LOGGER.error("Error in moderation: {}", e.getMessage(), e);
            throw new ModuleException(String.format(ERROR_MSG_FORMAT, "Moderation"),
                    InferenceErrorType.TEXT_MODERATION, e);
        }
    }
}