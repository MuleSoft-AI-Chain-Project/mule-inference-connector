package com.mulesoft.connectors.internal.llmmodels.groq.providers;

import com.mulesoft.connectors.internal.llmmodels.groq.GroqModelName;
import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import java.util.Arrays;
import java.util.Set;

public class GroqVisionModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(GroqModelName.values())
            .filter(GroqModelName::isVisionSupport).map(String::valueOf));
  }
} 