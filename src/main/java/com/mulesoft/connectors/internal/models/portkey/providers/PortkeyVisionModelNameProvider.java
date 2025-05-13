package com.mulesoft.connectors.internal.models.portkey.providers;

import com.mulesoft.connectors.internal.models.portkey.PortkeyModelName;
import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import java.util.Arrays;
import java.util.Set;

public class PortkeyVisionModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(PortkeyModelName.values())
            .filter(PortkeyModelName::isVisionSupport).map(String::valueOf));
  }
} 