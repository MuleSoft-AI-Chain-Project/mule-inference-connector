package com.mulesoft.connectors.inference.internal.helpers;

import com.mulesoft.connectors.inference.api.metadata.AdditionalAttributes;
import com.mulesoft.connectors.inference.api.metadata.ImageResponseAttributes;
import com.mulesoft.connectors.inference.api.metadata.LLMResponseAttributes;
import com.mulesoft.connectors.inference.api.metadata.TokenUsage;
import org.mule.runtime.api.metadata.MediaType;
import org.mule.runtime.extension.api.runtime.operation.Result;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.apache.commons.io.IOUtils.toInputStream;

public final class ResponseHelper {

  private ResponseHelper() {}

  public static Result<InputStream, LLMResponseAttributes> createLLMResponse(String response,
                                                                             TokenUsage tokenUsage,
                                                                             AdditionalAttributes responseAttributes) {
      return Result.<InputStream, LLMResponseAttributes>builder()
        .attributes(new LLMResponseAttributes(tokenUsage, responseAttributes))
        .attributesMediaType(MediaType.APPLICATION_JAVA)
        .output(toInputStream(response, StandardCharsets.UTF_8))
        .mediaType(MediaType.APPLICATION_JSON)
        .build();
  }

  public static Result<InputStream, ImageResponseAttributes> createImageGenerationLLMResponse(String response,
                                                                                              String model, String revisedPrompt) {
    return Result.<InputStream, ImageResponseAttributes>builder()
            .attributes(new ImageResponseAttributes(model, revisedPrompt))
            .attributesMediaType(MediaType.APPLICATION_JAVA)
            .output(toInputStream(response, StandardCharsets.UTF_8))
            .mediaType(MediaType.APPLICATION_JSON)
            .build();
  }
}
