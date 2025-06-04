package com.mulesoft.connectors.inference.internal.error.provider;

import org.mule.runtime.extension.api.annotation.error.ErrorTypeProvider;
import org.mule.runtime.extension.api.error.ErrorTypeDefinition;

import java.util.Set;

import static com.mulesoft.connectors.inference.internal.error.InferenceErrorType.INVALID_CONNECTION;
import static com.mulesoft.connectors.inference.internal.error.InferenceErrorType.RATE_LIMIT_EXCEEDED;
import static com.mulesoft.connectors.inference.internal.error.InferenceErrorType.TOXICITY_DETECTION_OPERATION_FAILURE;

public class ModerationErrorTypeProvider implements ErrorTypeProvider {

  @SuppressWarnings("rawtypes")
  public Set<ErrorTypeDefinition> getErrorTypes() {
    return Set.of(TOXICITY_DETECTION_OPERATION_FAILURE, INVALID_CONNECTION,RATE_LIMIT_EXCEEDED);
  }
}
