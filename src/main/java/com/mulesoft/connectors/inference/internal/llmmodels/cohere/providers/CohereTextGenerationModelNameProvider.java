package com.mulesoft.connectors.inference.internal.llmmodels.cohere.providers;

import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import com.mulesoft.connectors.inference.internal.llmmodels.cohere.CohereModelName;

import java.util.Arrays;
import java.util.Set;

public class CohereTextGenerationModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(CohereModelName.values())
        .filter(CohereModelName::supportsTextGeneration).sorted().map(String::valueOf));
  }
}
