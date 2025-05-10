/**
 * (c) 2003-2024 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.internal.models.moderation;


import com.mulesoft.connectors.internal.exception.error.ConfigValidationException;

import java.util.Arrays;
import java.util.stream.Stream;

@Deprecated
public enum ModerationModelType {
    OPENAI("OPENAI", getOpenAIModerationModelNameStream()),
    MISTRAL_AI("MISTRAL_AI", getMistralAIModerationModelNameStream());

  private final String value;
  private final Stream<String> modelNameStream;

  ModerationModelType(String value, Stream<String> modelNameStream) {
    this.value = value;
    this.modelNameStream = modelNameStream;
  }

  public String getValue() {
    return value;
  }

  public Stream<String> getModelNameStream() {
    return modelNameStream;
  }

  private static Stream<String> getOpenAIModerationModelNameStream() {
    return Arrays.stream(OpenAIModerationModelName.values()).map(String::valueOf);
  }

  private static Stream<String> getMistralAIModerationModelNameStream() {
    return Arrays.stream(MistralIModerationModelName.values()).map(String::valueOf);
  }

  public static ModerationModelType fromValue(String value) {
    return Arrays.stream(ModerationModelType.values())
        .filter(moderationModelType -> moderationModelType.value.equals(value))
        .findFirst()
        .orElseThrow(() -> new ConfigValidationException("Unsupported Moderation Model Type: " + value));
  }

  
  enum OpenAIModerationModelName {

    text_moderation_latest_legacy("text-moderation-latest"),
    omni_moderation_latest("omni-moderation-latest");
    

    private final String value;

    OpenAIModerationModelName(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return this.value;
    }
  }

  enum MistralIModerationModelName {

    mistral_moderation_latest("mistral-moderation-latest");
    
    private final String value;

    MistralIModerationModelName(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return this.value;
    }
  }

  
}
