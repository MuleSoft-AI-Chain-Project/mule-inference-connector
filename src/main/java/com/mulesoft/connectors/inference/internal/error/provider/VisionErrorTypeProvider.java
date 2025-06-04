package com.mulesoft.connectors.inference.internal.error.provider;

import static com.mulesoft.connectors.inference.internal.error.InferenceErrorType.INVALID_CONNECTION;
import static com.mulesoft.connectors.inference.internal.error.InferenceErrorType.INVALID_PROVIDER;
import static com.mulesoft.connectors.inference.internal.error.InferenceErrorType.RATE_LIMIT_EXCEEDED;
import static com.mulesoft.connectors.inference.internal.error.InferenceErrorType.READ_IMAGE_OPERATION_FAILURE;

import org.mule.runtime.extension.api.annotation.error.ErrorTypeProvider;
import org.mule.runtime.extension.api.error.ErrorTypeDefinition;

import java.util.Set;

public class VisionErrorTypeProvider implements ErrorTypeProvider {

  @SuppressWarnings("rawtypes")
  public Set<ErrorTypeDefinition> getErrorTypes() {
    return Set.of(READ_IMAGE_OPERATION_FAILURE, INVALID_CONNECTION, INVALID_PROVIDER, RATE_LIMIT_EXCEEDED);
  }
}
