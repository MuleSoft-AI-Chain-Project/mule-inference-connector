package com.mulesoft.connectors.inference.internal.error.provider;

import org.mule.runtime.extension.api.annotation.error.ErrorTypeProvider;
import org.mule.runtime.extension.api.error.ErrorTypeDefinition;

import java.util.Set;

import static com.mulesoft.connectors.inference.internal.error.InferenceErrorType.CHAT_OPERATION_FAILURE;
import static com.mulesoft.connectors.inference.internal.error.InferenceErrorType.INVALID_CONNECTION;
import static com.mulesoft.connectors.inference.internal.error.InferenceErrorType.INVALID_PROVIDER;
import static com.mulesoft.connectors.inference.internal.error.InferenceErrorType.MCP_TOOLS_OPERATION_FAILURE;
import static com.mulesoft.connectors.inference.internal.error.InferenceErrorType.RATE_LIMIT_EXCEEDED;
import static com.mulesoft.connectors.inference.internal.error.InferenceErrorType.TOOLS_OPERATION_FAILURE;

public class TextGenerationErrorTypeProvider implements ErrorTypeProvider {

  @SuppressWarnings("rawtypes")
  public Set<ErrorTypeDefinition> getErrorTypes() {
    return Set.of(CHAT_OPERATION_FAILURE, TOOLS_OPERATION_FAILURE,MCP_TOOLS_OPERATION_FAILURE,INVALID_CONNECTION,
            INVALID_PROVIDER,RATE_LIMIT_EXCEEDED);
  }
}
