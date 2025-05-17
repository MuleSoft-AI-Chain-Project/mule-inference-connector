package com.mulesoft.connectors.inference.internal.llmmodels.azure.providers;

import com.mulesoft.connectors.inference.internal.llmmodels.azure.AzureOpenAIModelName;
import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import java.util.Arrays;
import java.util.Set;

public class AzureOpenAITextGenerationModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(AzureOpenAIModelName.values())
            .filter(AzureOpenAIModelName::isTextGenerationSupport).map(String::valueOf));
  }
} 