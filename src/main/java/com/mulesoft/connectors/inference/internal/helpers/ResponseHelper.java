package com.mulesoft.connectors.inference.internal.helpers;

import com.mulesoft.connectors.inference.api.metadata.LLMResponseAttributes;
import com.mulesoft.connectors.inference.api.metadata.TokenUsage;
import org.mule.runtime.extension.api.runtime.operation.Result;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.io.IOUtils.toInputStream;

public final class ResponseHelper {

  private ResponseHelper() {}


  public static Result<InputStream, LLMResponseAttributes> createLLMResponse(String response,
                                                                             TokenUsage tokenUsage,
                                                                             Map<String, String> responseAttributes) {
      return Result.<InputStream, LLMResponseAttributes>builder()
        .attributes(new LLMResponseAttributes(tokenUsage, (HashMap<String, String>) responseAttributes))
        .attributesMediaType(org.mule.runtime.api.metadata.MediaType.APPLICATION_JAVA)
        .output(toInputStream(response, StandardCharsets.UTF_8))
        .mediaType(org.mule.runtime.api.metadata.MediaType.APPLICATION_JSON)
        .build();
  }


  public static Result<InputStream, Map<String, Object>> createLLMResponse(String response,
                                                                           Map<String, Object> responseAttributes) {
    return Result.<InputStream, Map<String, Object>>builder()
        .attributes(responseAttributes)
        .attributesMediaType(org.mule.runtime.api.metadata.MediaType.APPLICATION_JAVA)
        .output(toInputStream(response, StandardCharsets.UTF_8))
        .mediaType(org.mule.runtime.api.metadata.MediaType.APPLICATION_JSON)
        .build();
  }
}
