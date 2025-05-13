package com.mulesoft.connectors.internal.llmmodels.azure.providers;

import com.mulesoft.connectors.internal.llmmodels.azure.AzureAIFoundryModelName;
import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import java.util.Arrays;
import java.util.Set;

public class AzureAIFoundryTextGenerationModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(AzureAIFoundryModelName.values())
            .filter(AzureAIFoundryModelName::isTextGenerationSupport).map(String::valueOf));
  }
} 