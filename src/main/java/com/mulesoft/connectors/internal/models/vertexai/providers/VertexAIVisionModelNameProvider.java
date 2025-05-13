package com.mulesoft.connectors.internal.models.vertexai.providers;

import com.mulesoft.connectors.internal.models.vertexai.VertexAIModelName;
import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import java.util.Arrays;
import java.util.Set;

public class VertexAIVisionModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(VertexAIModelName.values())
            .filter(VertexAIModelName::isVisionSupport).map(String::valueOf));
  }
} 