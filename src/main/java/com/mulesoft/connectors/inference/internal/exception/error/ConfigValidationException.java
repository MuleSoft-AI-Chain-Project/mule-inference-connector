/**
 * (c) 2003-2024 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.inference.internal.exception.error;

import org.mule.runtime.extension.api.exception.ModuleException;
import com.mulesoft.connectors.inference.internal.exception.InferenceErrorType;

public class ConfigValidationException extends ModuleException {

  public ConfigValidationException(String message) {
//aw    super(message, InferenceErrorType.VALIDATION_FAILURE);
    super(message, InferenceErrorType.CHAT_COMPLETION);
  }
}
