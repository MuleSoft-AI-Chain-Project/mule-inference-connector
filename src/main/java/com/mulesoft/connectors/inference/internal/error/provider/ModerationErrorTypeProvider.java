package com.mulesoft.connectors.inference.internal.error.provider;

import org.mule.runtime.extension.api.annotation.error.ErrorTypeProvider;
import org.mule.runtime.extension.api.error.ErrorTypeDefinition;
import org.mule.runtime.extension.api.error.MuleErrors;

import java.util.Set;

import static com.mulesoft.connectors.inference.internal.error.InferenceErrorType.TOXICITY_DETECTION_OPERATION_FAILURE;

public class ModerationErrorTypeProvider implements ErrorTypeProvider {

  @SuppressWarnings("rawtypes")
  public Set<ErrorTypeDefinition> getErrorTypes() {
    return Set.of(TOXICITY_DETECTION_OPERATION_FAILURE, MuleErrors.CONNECTIVITY);
  }
}
