package com.mulesoft.connectors.inference.internal.llmmodels.fireworks.providers;

import com.mulesoft.connectors.inference.internal.llmmodels.fireworks.FireworksModelName;
import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import java.util.Arrays;
import java.util.Set;

public class FireworksTextGenerationModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(FireworksModelName.values())
            .filter(FireworksModelName::isTextGenerationSupport).map(String::valueOf));
  }
} 