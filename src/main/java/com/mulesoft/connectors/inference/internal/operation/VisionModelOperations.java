package com.mulesoft.connectors.inference.internal.operation;

import com.mulesoft.connectors.inference.api.metadata.LLMResponseAttributes;
import com.mulesoft.connectors.inference.internal.connection.VisionModelConnection;
import com.mulesoft.connectors.inference.internal.exception.InferenceErrorType;
import org.mule.runtime.extension.api.annotation.Alias;
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

/**
 * This class contains operations for the inference connector.
 * Each public method represents an extension operation.
 */
public class VisionModelOperations {
	
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
            @Connection VisionModelConnection connection,
            @Content String prompt,
            @Content(primary = true) @DisplayName("Image") @Summary("An Image URL or a Base64 Image") String imageUrl) throws ModuleException {
        try { 
           return connection.getService().getVisionModelServiceInstance().readImage(connection,prompt,imageUrl);
        } catch (Exception e) {
            throw new ModuleException("Error in executing read image operation",
                    InferenceErrorType.VISION_FAILURE, e);
        }
    }
}