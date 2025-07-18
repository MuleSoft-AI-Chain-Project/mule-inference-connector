package com.mulesoft.connectors.inference.internal.llmmodels.deepinfra.providers;

import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import com.mulesoft.connectors.inference.internal.llmmodels.deepinfra.DeepInfraModelName;

import java.util.Arrays;
import java.util.Set;

public class DeepInfraTextGenerationModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(DeepInfraModelName.values())
        .filter(DeepInfraModelName::supportsTextGeneration).sorted().map(String::valueOf));
  }
}
