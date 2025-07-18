package com.mulesoft.connectors.inference.internal.llmmodels.llamaapi.providers;

import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import com.mulesoft.connectors.inference.internal.llmmodels.llamaapi.LlmAPIModelName;

import java.util.Arrays;
import java.util.Set;

public class LlmAPITextGenerationModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(LlmAPIModelName.values())
        .filter(LlmAPIModelName::supportsTextGeneration).sorted().map(String::valueOf));
  }
}
