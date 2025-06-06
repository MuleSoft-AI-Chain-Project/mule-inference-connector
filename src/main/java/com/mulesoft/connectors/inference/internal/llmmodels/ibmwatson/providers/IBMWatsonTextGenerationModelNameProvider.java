package com.mulesoft.connectors.inference.internal.llmmodels.ibmwatson.providers;

import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import com.mulesoft.connectors.inference.internal.llmmodels.ibmwatson.IBMWatsonModelName;

import java.util.Arrays;
import java.util.Set;

public class IBMWatsonTextGenerationModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(IBMWatsonModelName.values())
        .filter(IBMWatsonModelName::isTextGenerationSupport).map(String::valueOf));
  }
}
