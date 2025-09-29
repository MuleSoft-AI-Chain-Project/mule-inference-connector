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

import com.mulesoft.connectors.inference.api.metadata.ImageResponseAttributes;
import com.mulesoft.connectors.inference.internal.connection.types.ImageGenerationConnection;
import com.mulesoft.connectors.inference.internal.error.InferenceErrorType;
import com.mulesoft.connectors.inference.internal.error.provider.ImageGenerationErrorTypeProvider;

import java.io.InputStream;

/**
 * This class contains operations for the inference connector. Each public method represents an extension operation.
 */
@Throws(ImageGenerationErrorTypeProvider.class)
public class ImageGenerationModelOperations {

  /**
   * Generate images based on a text prompt using AI image generation models
   *
   * @param connection the connector connection
   * @param prompt the text prompt describing the desired image
   * @return result containing the generated image response
   * @throws ModuleException if an error occurs during the operation
   */
  @MediaType(value = APPLICATION_JSON, strict = false)
  @Alias("Generate-image")
  @DisplayName("[Image] Generate (only Base64)")
  @OutputJsonType(schema = "api/response/ResponseImageGeneration.json")
  public Result<InputStream, ImageResponseAttributes> generateImage(
                                                                    @Connection ImageGenerationConnection connection,
                                                                    @Content String prompt,
                                                                    @Content @Optional @DisplayName("Additional Request Attributes") @Summary("JSON object with additional request attributes that will be flattened into the root level of the request payload") InputStream additionalRequestAttributes)
      throws ModuleException {
    try {
      return connection.getService().getImageGenerationServiceInstance().executeGenerateImage(connection, prompt,
                                                                                              additionalRequestAttributes);
    } catch (ModuleException e) {
      throw e;
    } catch (Exception e) {
      throw new ModuleException("Error in executing generate image operation.", InferenceErrorType.IMAGE_GENERATION_FAILURE, e);
    }
  }
}
