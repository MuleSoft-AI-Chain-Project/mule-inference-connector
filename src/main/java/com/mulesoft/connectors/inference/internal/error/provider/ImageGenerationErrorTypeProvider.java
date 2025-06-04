package com.mulesoft.connectors.inference.internal.error.provider;

import static com.mulesoft.connectors.inference.internal.error.InferenceErrorType.IMAGE_GENERATION_FAILURE;
import static com.mulesoft.connectors.inference.internal.error.InferenceErrorType.INVALID_CONNECTION;
import static com.mulesoft.connectors.inference.internal.error.InferenceErrorType.RATE_LIMIT_EXCEEDED;

import org.mule.runtime.extension.api.annotation.error.ErrorTypeProvider;
import org.mule.runtime.extension.api.error.ErrorTypeDefinition;

import java.util.Set;

public class ImageGenerationErrorTypeProvider implements ErrorTypeProvider {

  @SuppressWarnings("rawtypes")
  public Set<ErrorTypeDefinition> getErrorTypes() {
    return Set.of(IMAGE_GENERATION_FAILURE, INVALID_CONNECTION, RATE_LIMIT_EXCEEDED);
  }
}
