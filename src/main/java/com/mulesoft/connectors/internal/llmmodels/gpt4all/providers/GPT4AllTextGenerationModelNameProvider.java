package com.mulesoft.connectors.internal.llmmodels.gpt4all.providers;

import com.mulesoft.connectors.internal.llmmodels.gpt4all.GPT4ALLModelName;
import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import java.util.Arrays;
import java.util.Set;

public class GPT4AllTextGenerationModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(GPT4ALLModelName.values())
            .filter(GPT4ALLModelName::isTextGenerationSupport).map(String::valueOf));
  }
} 