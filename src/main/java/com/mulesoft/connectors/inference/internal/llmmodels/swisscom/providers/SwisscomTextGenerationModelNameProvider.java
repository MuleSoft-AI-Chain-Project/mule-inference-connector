package com.mulesoft.connectors.inference.internal.llmmodels.swisscom.providers;

import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import com.mulesoft.connectors.inference.internal.llmmodels.swisscom.SwisscomModelName;

import java.util.Arrays;
import java.util.Set;

public class SwisscomTextGenerationModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(SwisscomModelName.values())
        .filter(SwisscomModelName::supportsTextGeneration).sorted().map(String::valueOf));
  }
}
