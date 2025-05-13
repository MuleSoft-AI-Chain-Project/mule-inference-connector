/**
 * (c) 2003-2024 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.internal.models.images;


import com.mulesoft.connectors.internal.exception.error.ConfigValidationException;

import java.util.Arrays;
import java.util.stream.Stream;

@Deprecated
public enum ModelType {
      OPENAI("OPENAI", getOpenAIModelNameStream()),
      HUGGING_FACE("HUGGING_FACE", getHuggingFaceModelNameStream()),
      STABILITY_AI("STABILITY_AI", getStabilityModelNameStream()),
      XAI("XAI", getXAIModelNameStream()),

  ;

  private final String value;
  private final Stream<String> modelNameStream;

  ModelType(String value, Stream<String> modelNameStream) {
    this.value = value;
    this.modelNameStream = modelNameStream;
  }

  public String getValue() {
    return value;
  }

  public Stream<String> getModelNameStream() {
    return modelNameStream;
  }

  private static Stream<String> getHuggingFaceModelNameStream() {
    return Arrays.stream(HuggingFaceModelName.values()).map(String::valueOf);
  }

  private static Stream<String> getXAIModelNameStream() {
    return Arrays.stream(XAIModelName.values()).map(String::valueOf);
  }

  private static Stream<String> getOpenAIModelNameStream() {
    return Arrays.stream(OpenAIModelName.values()).map(String::valueOf);
  }

  private static Stream<String> getStabilityModelNameStream() {
    return Arrays.stream(StabilityModelName.values()).map(String::valueOf);
  }


  public static ModelType fromValue(String value) {
    return Arrays.stream(ModelType.values())
        .filter(langchainLLMType -> langchainLLMType.value.equals(value))
        .findFirst()
        .orElseThrow(() -> new ConfigValidationException("Unsupported LLM Type: " + value));
  }

  enum OpenAIModelName {

    DALL_E_3("dall-e-3"),
    DALL_E_2("dall-e-2")
    ;

    private final String value;

    OpenAIModelName(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return this.value;
    }
  }

  enum HuggingFaceModelName {
    black_forest_labs_flux_1_dev("black-forest-labs/FLUX.1-dev"),
    stabilityai_stable_diffusion_35_large("stabilityai/stable-diffusion-3.5-large"),
    ionet_official_bc8_alpha("ionet-official/bc8-alpha"),
    ;

    private final String value;

    HuggingFaceModelName(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return this.value;
    }
  }

  enum StabilityModelName {
    sd3_5_large("sd3.5-large"),
    sd3_medium("sd3-medium"),
    sd3_5_large_turbo("sd3-large-turbo"),
    ;

    private final String value;

    StabilityModelName(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return this.value;
    }
  }

  enum XAIModelName {

    grok_2_image("grok-2-image"),
    ;

    private final String value;

    XAIModelName(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return this.value;
    }
  }

}
