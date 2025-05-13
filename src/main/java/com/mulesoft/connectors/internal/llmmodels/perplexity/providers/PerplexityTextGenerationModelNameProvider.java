package com.mulesoft.connectors.internal.llmmodels.perplexity.providers;

import com.mulesoft.connectors.internal.llmmodels.perplexity.PerplexityModelName;
import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import java.util.Arrays;
import java.util.Set;

public class PerplexityTextGenerationModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(PerplexityModelName.values())
            .filter(PerplexityModelName::isTextGenerationSupport).map(String::valueOf));
  }
} 