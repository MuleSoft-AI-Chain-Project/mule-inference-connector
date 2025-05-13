package com.mulesoft.connectors.internal.llmmodels.nvidia.providers;

import com.mulesoft.connectors.internal.llmmodels.nvidia.NvidiaModelName;
import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import java.util.Arrays;
import java.util.Set;

public class NvidiaTextGenerationModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(NvidiaModelName.values())
            .filter(NvidiaModelName::isTextGenerationSupport).map(String::valueOf));
  }
} 