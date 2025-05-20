/**
 * (c) 2003-2024 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.inference.internal.exception;

import org.mule.runtime.extension.api.error.ErrorTypeDefinition;
import org.mule.runtime.extension.api.error.MuleErrors;

import java.util.Optional;

public enum InferenceErrorType implements ErrorTypeDefinition<InferenceErrorType> {

  CHAT_OPERATION_FAILURE(MuleErrors.ANY),
  TOOLS_OPERATION_FAILURE(MuleErrors.ANY),
  TEXT_MODERATION_FAILURE(MuleErrors.ANY),
  VISION_FAILURE(MuleErrors.ANY),
  IMAGE_GENERATION_FAILURE(MuleErrors.ANY),
  INVALID_PROVIDER(MuleErrors.CONNECTIVITY),
  INVALID_CONNECTION(MuleErrors.CONNECTIVITY)  ;

  private final ErrorTypeDefinition<? extends Enum<?>> parent;

  @Override
  public Optional<ErrorTypeDefinition<? extends Enum<?>>> getParent() {
    return Optional.of(parent);
  }

  InferenceErrorType(ErrorTypeDefinition<? extends Enum<?>> parent) {
    this.parent = parent;
  }
}
