package com.mulesoft.connectors.inference.internal.llmmodels.perplexity.providers;

import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import com.mulesoft.connectors.inference.internal.llmmodels.perplexity.PerplexityModelName;

import java.util.Arrays;
import java.util.Set;

public class PerplexityTextGenerationModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(PerplexityModelName.values())
        .filter(PerplexityModelName::supportsTextGeneration).sorted().map(String::valueOf));
  }
}
