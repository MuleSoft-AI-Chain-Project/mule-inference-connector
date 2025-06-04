package com.mulesoft.connectors.inference.internal.llmmodels.together.providers;

import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import com.mulesoft.connectors.inference.internal.llmmodels.together.TogetherModelName;

import java.util.Arrays;
import java.util.Set;

public class TogetherTextGenerationModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(TogetherModelName.values())
        .filter(TogetherModelName::isTextGenerationSupport).map(String::valueOf));
  }
}
