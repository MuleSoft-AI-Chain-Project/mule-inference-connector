package com.mulesoft.connectors.inference.internal.error.provider;

import org.mule.runtime.extension.api.annotation.error.ErrorTypeProvider;
import org.mule.runtime.extension.api.error.ErrorTypeDefinition;
import org.mule.runtime.extension.api.error.MuleErrors;

import java.util.Set;

import static com.mulesoft.connectors.inference.internal.error.InferenceErrorType.IMAGE_GENERATION_FAILURE;

public class ImageGenerationErrorTypeProvider implements ErrorTypeProvider {

  @SuppressWarnings("rawtypes")
  public Set<ErrorTypeDefinition> getErrorTypes() {
    return Set.of(IMAGE_GENERATION_FAILURE, MuleErrors.CONNECTIVITY);
  }
}
