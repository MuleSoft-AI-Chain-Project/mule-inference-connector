package com.mulesoft.connectors.inference.internal.llmmodels.ollama.providers;

import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import com.mulesoft.connectors.inference.internal.llmmodels.ollama.OllamaModelName;

import java.util.Arrays;
import java.util.Set;

public class OllamaVisionModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(OllamaModelName.values())
        .filter(OllamaModelName::supportsVision).map(String::valueOf));
  }
}
