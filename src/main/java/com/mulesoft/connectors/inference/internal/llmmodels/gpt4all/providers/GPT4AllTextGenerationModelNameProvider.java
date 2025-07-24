package com.mulesoft.connectors.inference.internal.llmmodels.gpt4all.providers;

import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import com.mulesoft.connectors.inference.internal.llmmodels.gpt4all.GPT4ALLModelName;

import java.util.Arrays;
import java.util.Set;

public class GPT4AllTextGenerationModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(GPT4ALLModelName.values())
        .filter(GPT4ALLModelName::supportsTextGeneration).sorted().map(String::valueOf));
  }
}
