package com.mulesoft.connectors.inference.internal.llmmodels.vertexai.providers;

import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import com.mulesoft.connectors.inference.internal.llmmodels.vertexai.VertexAIExpressModelName;

import java.util.Arrays;
import java.util.Set;

public class VertexAIExpressTextGenerationModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(VertexAIExpressModelName.values())
        .filter(VertexAIExpressModelName::isTextGenerationSupport).map(String::valueOf));
  }
}
