package com.mulesoft.connectors.internal.models.ollama.providers;

import com.mulesoft.connectors.internal.models.ollama.OllamaModelName;
import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import java.util.Arrays;
import java.util.Set;

public class OllamaVisionModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(OllamaModelName.values())
            .filter(OllamaModelName::isVisionSupport).map(String::valueOf));
  }
} 