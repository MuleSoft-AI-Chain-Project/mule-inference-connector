package com.mulesoft.connectors.inference.internal.llmmodels.xinference.providers;

import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import com.mulesoft.connectors.inference.internal.llmmodels.xinference.XInferenceModelName;

import java.util.Arrays;
import java.util.Set;

public class XInferenceTextGenerationModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(XInferenceModelName.values())
        .filter(XInferenceModelName::isTextGenerationSupport).map(String::valueOf));
  }
}
