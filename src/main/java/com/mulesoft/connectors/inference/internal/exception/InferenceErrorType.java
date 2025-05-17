/**
 * (c) 2003-2024 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.inference.internal.exception;

import org.mule.runtime.extension.api.error.ErrorTypeDefinition;

public enum InferenceErrorType implements ErrorTypeDefinition<InferenceErrorType> {

  CHAT_COMPLETION,
  TEXT_MODERATION,
  VISION,
  IMAGE_GENERATION
}
