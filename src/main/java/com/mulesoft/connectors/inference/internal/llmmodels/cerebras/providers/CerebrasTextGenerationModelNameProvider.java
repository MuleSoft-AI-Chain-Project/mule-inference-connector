package com.mulesoft.connectors.inference.internal.llmmodels.cerebras.providers;

import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import com.mulesoft.connectors.inference.internal.llmmodels.cerebras.CerebrasModelName;

import java.util.Arrays;
import java.util.Set;

public class CerebrasTextGenerationModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(CerebrasModelName.values())
        .filter(CerebrasModelName::isTextGenerationSupport).map(String::valueOf));
  }
}
