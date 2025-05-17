package com.mulesoft.connectors.inference.internal.llmmodels.anthropic.providers;

import com.mulesoft.connectors.inference.internal.llmmodels.anthropic.AnthropicModelName;
import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import java.util.Arrays;
import java.util.Set;

public class AnthropicTextGenerationModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(AnthropicModelName.values())
            .filter(AnthropicModelName::isTextGenerationSupport).map(String::valueOf));
  }
} 