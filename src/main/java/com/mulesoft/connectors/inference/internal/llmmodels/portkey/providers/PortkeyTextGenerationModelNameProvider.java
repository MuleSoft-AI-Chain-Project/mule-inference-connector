package com.mulesoft.connectors.inference.internal.llmmodels.portkey.providers;

import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import com.mulesoft.connectors.inference.internal.llmmodels.portkey.PortkeyModelName;

import java.util.Arrays;
import java.util.Set;

public class PortkeyTextGenerationModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(PortkeyModelName.values())
        .filter(PortkeyModelName::isTextGenerationSupport).map(String::valueOf));
  }
}
