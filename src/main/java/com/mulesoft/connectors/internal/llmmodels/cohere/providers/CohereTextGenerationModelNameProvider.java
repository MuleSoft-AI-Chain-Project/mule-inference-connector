package com.mulesoft.connectors.internal.llmmodels.cohere.providers;

import com.mulesoft.connectors.internal.llmmodels.cohere.CohereModelName;
import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import java.util.Arrays;
import java.util.Set;

public class CohereTextGenerationModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(CohereModelName.values())
            .filter(CohereModelName::isTextGenerationSupport).map(String::valueOf));
  }
} 