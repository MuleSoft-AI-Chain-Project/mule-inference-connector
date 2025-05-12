package com.mulesoft.connectors.internal.operations;

import com.mulesoft.connectors.internal.api.metadata.LLMResponseAttributes;
import com.mulesoft.connectors.internal.connection.BaseConnection;
import com.mulesoft.connectors.internal.connection.ChatCompletionBase;
import com.mulesoft.connectors.internal.exception.InferenceErrorType;
import com.mulesoft.connectors.internal.utils.*;
import org.json.JSONObject;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.metadata.fixed.OutputJsonType;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.Content;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.exception.ModuleException;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.URL;

import static com.mulesoft.connectors.internal.utils.ConnectionUtils.executeRestImageGeneration;
import static com.mulesoft.connectors.internal.utils.PayloadUtils.createRequestImageGeneration;
import static org.mule.runtime.extension.api.annotation.param.MediaType.APPLICATION_JSON;

/**
 * This class contains operations for the inference connector.
 * Each public method represents an extension operation.
 */
public class ImageGenerationModelOperations {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageGenerationModelOperations.class);
    private static final String ERROR_MSG_FORMAT = "%s result error";

    /**
     * Chat completions by messages array including system, users messages i.e. conversation history
     * @param connection the connector connection
     * @param prompt the users prompt
     * @return result containing the LLM response
     * @throws ModuleException if an error occurs during the operation
     */
    @MediaType(value = APPLICATION_JSON, strict = false)
    @Alias("Generate-image")
    @DisplayName("[Image] Generate (only Base64)")
    @OutputJsonType(schema = "api/response/Response.json")
    public Result<InputStream, LLMResponseAttributes> generateImage(
            @Connection BaseConnection connection,
            @Content String prompt) throws ModuleException {
        try {

            JSONObject requestJson = createRequestImageGeneration(connection.getInferenceType(), prompt);

            String response;
            ChatCompletionBase baseConnection = ProviderUtils.convertToBaseConnection(connection);

            URL imageGenerationUrl = ConnectionUtils.getConnectionURLImageGeneration(baseConnection);
            LOGGER.debug("Generate Image with {}", imageGenerationUrl);

            JSONObject payload = PayloadUtils.buildPayloadImageGeneration(connection, requestJson);

            response = executeRestImageGeneration(imageGenerationUrl, baseConnection, payload.toString());

            LOGGER.debug("Generate Image result {}", response);

            return ResponseUtils.processImageGenResponse(response, baseConnection);
        } catch (Exception e) {
            LOGGER.error("Error in Generate Image: {}", e.getMessage(), e);
            throw new ModuleException(String.format(ERROR_MSG_FORMAT, "Generate Image"),
                    InferenceErrorType.IMAGE_GENERATION, e);
        }
    }
}