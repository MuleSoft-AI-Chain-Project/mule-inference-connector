package com.mulesoft.connectors.inference.internal.operation;

import static org.mule.runtime.extension.api.annotation.param.MediaType.APPLICATION_JSON;

import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.error.Throws;
import org.mule.runtime.extension.api.annotation.metadata.fixed.OutputJsonType;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.Content;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.mule.runtime.extension.api.exception.ModuleException;
import org.mule.runtime.extension.api.runtime.operation.Result;

import com.mulesoft.connectors.inference.api.metadata.LLMResponseAttributes;
import com.mulesoft.connectors.inference.internal.connection.types.VisionModelConnection;
import com.mulesoft.connectors.inference.internal.error.InferenceErrorType;
import com.mulesoft.connectors.inference.internal.error.provider.VisionErrorTypeProvider;

import java.io.InputStream;

/**
 * This class contains operations for the inference connector. Each public method represents an extension operation.
 */
@Throws(VisionErrorTypeProvider.class)
public class VisionModelOperations {

  /**
   * Analyze and describe images using AI vision models based on a text prompt and image input
   *
   * @param connection the connector connection
   * @param prompt the text prompt describing what to analyze or ask about the image
   * @param imageUrl the image URL or Base64 encoded image to be analyzed by the Vision Model
   * @return result containing the vision model response
   * @throws ModuleException if an error occurs during the operation
   */
  @MediaType(value = APPLICATION_JSON, strict = false)
  @Alias("Read-image")
  @DisplayName("[Image] Read by (Url or Base64)")
  @OutputJsonType(schema = "api/response/Response.json")
  public Result<InputStream, LLMResponseAttributes> readImage(
                                                              @Connection VisionModelConnection connection,
                                                              @Content String prompt,
                                                              @Content(
                                                                  primary = true) @DisplayName("Image") @Summary("An Image URL or a Base64 Image") String imageUrl,
                                                              @Content @Optional @DisplayName("Additional Request Attributes") @Summary("JSON object with additional request attributes that will be flattened into the root level of the request payload") InputStream additionalRequestAttributes)
      throws ModuleException {
    try {
      return connection.getService().getVisionModelServiceInstance().readImage(connection, prompt, imageUrl,
                                                                               additionalRequestAttributes);
    } catch (ModuleException e) {
      throw e;
    } catch (Exception e) {
      throw new ModuleException("Error in executing read image operation",
                                InferenceErrorType.READ_IMAGE_OPERATION_FAILURE, e);
    }
  }
}
