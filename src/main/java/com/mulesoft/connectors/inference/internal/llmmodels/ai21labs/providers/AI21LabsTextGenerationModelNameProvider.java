package com.mulesoft.connectors.inference.internal.llmmodels.ai21labs.providers;

import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import com.mulesoft.connectors.inference.internal.llmmodels.ai21labs.AI21LabsModelName;

import java.util.Arrays;
import java.util.Set;

public class AI21LabsTextGenerationModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(AI21LabsModelName.values())
        .filter(AI21LabsModelName::isTextGenerationSupport).map(String::valueOf));
  }
}
