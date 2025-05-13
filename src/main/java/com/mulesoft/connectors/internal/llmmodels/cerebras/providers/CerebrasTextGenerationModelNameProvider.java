package com.mulesoft.connectors.internal.llmmodels.cerebras.providers;

import com.mulesoft.connectors.internal.llmmodels.cerebras.CerebrasModelName;
import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import java.util.Arrays;
import java.util.Set;

public class CerebrasTextGenerationModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(CerebrasModelName.values())
            .filter(CerebrasModelName::isTextGenerationSupport).map(String::valueOf));
  }
} 