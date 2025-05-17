package com.mulesoft.connectors.inference.internal.llmmodels.huggingface.providers;

import com.mulesoft.connectors.inference.internal.llmmodels.huggingface.HuggingFaceModelName;
import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import java.util.Arrays;
import java.util.Set;

public class HuggingFaceTextGenerationModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(HuggingFaceModelName.values())
            .filter(HuggingFaceModelName::isTextGenerationSupport).map(String::valueOf));
  }
} 