package com.mulesoft.connectors.inference.internal.error.provider;

import org.mule.runtime.extension.api.annotation.error.ErrorTypeProvider;
import org.mule.runtime.extension.api.error.ErrorTypeDefinition;
import org.mule.runtime.extension.api.error.MuleErrors;

import java.util.Set;

import static com.mulesoft.connectors.inference.internal.error.InferenceErrorType.CHAT_OPERATION_FAILURE;
import static com.mulesoft.connectors.inference.internal.error.InferenceErrorType.MCP_TOOLS_OPERATION_FAILURE;
import static com.mulesoft.connectors.inference.internal.error.InferenceErrorType.TOOLS_OPERATION_FAILURE;

public class TextGenerationErrorTypeProvider implements ErrorTypeProvider {

  @SuppressWarnings("rawtypes")
  public Set<ErrorTypeDefinition> getErrorTypes() {
    return Set.of(CHAT_OPERATION_FAILURE, TOOLS_OPERATION_FAILURE,MCP_TOOLS_OPERATION_FAILURE, MuleErrors.CONNECTIVITY);
  }
}
