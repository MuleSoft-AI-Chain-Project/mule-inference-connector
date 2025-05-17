package com.mulesoft.connectors.inference.internal.operations;

import com.mulesoft.connectors.inference.api.metadata.LLMResponseAttributes;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.exception.InferenceErrorType;
import com.mulesoft.connectors.inference.internal.utils.ConnectionUtils;
import com.mulesoft.connectors.inference.internal.utils.PayloadUtils;
import com.mulesoft.connectors.inference.internal.utils.ResponseUtils;
import org.json.JSONArray;
import org.json.JSONObject;
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

import static com.mulesoft.connectors.inference.internal.utils.PayloadUtils.createRequestImageURL;
import static org.mule.runtime.extension.api.annotation.param.MediaType.APPLICATION_JSON;

/**
 * This class contains operations for the inference connector.
 * Each public method represents an extension operation.
 */
public class VisionModelOperations {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(VisionModelOperations.class);
    private static final String ERROR_MSG_FORMAT = "%s result error";

    /**
     * Chat completions by messages array including system, users messages i.e. conversation history
     * @param connection the connector connection
     * @param prompt the users prompt
     * @param imageUrl the image Url to be sent to the Vision Model
     * @return result containing the LLM response
     * @throws ModuleException if an error occurs during the operation
     */
    @MediaType(value = APPLICATION_JSON, strict = false)
    @Alias("Read-image")
    @DisplayName("[Image] Read by (Url or Base64)")
    @OutputJsonType(schema = "api/response/Response.json")
    public Result<InputStream, LLMResponseAttributes> readImage(
            @Connection TextGenerationConnection connection,
            @Content String prompt,
            @Content(primary = true) @DisplayName("Image") @Summary("An Image URL or a Base64 Image") String imageUrl) throws ModuleException {
        try { 

            JSONArray messagesArray = createRequestImageURL(connection, prompt, imageUrl);

            URL chatCompUrl = new URL(connection.getApiURL());
            LOGGER.debug("Read Image with {}", chatCompUrl);

            JSONObject payload = PayloadUtils.buildPayload(connection, messagesArray, null);
            LOGGER.debug("payload sent to the LLM {}", payload);
            
            String response = ConnectionUtils.executeREST(chatCompUrl, connection, payload.toString());

            LOGGER.debug("Read Image result {}", response);
            return ResponseUtils.processLLMResponse(response, connection);
        } catch (Exception e) {
            LOGGER.error("Error in Read Image: {}", e.getMessage(), e);
            throw new ModuleException(String.format(ERROR_MSG_FORMAT, "Read Image"),
                    InferenceErrorType.VISION, e);
        }
    }
}