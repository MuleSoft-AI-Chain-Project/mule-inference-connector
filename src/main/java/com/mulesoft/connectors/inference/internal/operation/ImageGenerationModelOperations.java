package com.mulesoft.connectors.inference.internal.operation;

import com.mulesoft.connectors.inference.api.metadata.ImageResponseAttributes;
import com.mulesoft.connectors.inference.internal.connection.ImageGenerationConnection;
import com.mulesoft.connectors.inference.internal.error.InferenceErrorType;
import com.mulesoft.connectors.inference.internal.error.provider.ImageGenerationErrorTypeProvider;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.error.Throws;
import org.mule.runtime.extension.api.annotation.metadata.fixed.OutputJsonType;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.Content;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.exception.ModuleException;
import org.mule.runtime.extension.api.runtime.operation.Result;

import java.io.InputStream;

import static org.mule.runtime.extension.api.annotation.param.MediaType.APPLICATION_JSON;

/**
 * This class contains operations for the inference connector.
 * Each public method represents an extension operation.
 */
@Throws(ImageGenerationErrorTypeProvider.class)
public class ImageGenerationModelOperations {

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
    public Result<InputStream, ImageResponseAttributes> generateImage(
            @Connection ImageGenerationConnection connection,
            @Content String prompt) throws ModuleException {
        try {
            return connection.getService().getImageGenerationServiceInstance().executeGenerateImage(connection,prompt);
        } catch (ModuleException e) {
            throw e;
        } catch (Exception e) {
            throw new ModuleException("Error in executing generate image operation.", InferenceErrorType.IMAGE_GENERATION_FAILURE, e);
        }
    }
}