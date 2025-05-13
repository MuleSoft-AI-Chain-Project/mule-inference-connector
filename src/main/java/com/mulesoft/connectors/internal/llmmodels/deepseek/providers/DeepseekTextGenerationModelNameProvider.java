package com.mulesoft.connectors.internal.llmmodels.deepseek.providers;

import com.mulesoft.connectors.internal.llmmodels.deepseek.DeepseekModelName;
import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import java.util.Arrays;
import java.util.Set;

public class DeepseekTextGenerationModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(DeepseekModelName.values())
            .filter(DeepseekModelName::isTextGenerationSupport).map(String::valueOf));
  }
} 