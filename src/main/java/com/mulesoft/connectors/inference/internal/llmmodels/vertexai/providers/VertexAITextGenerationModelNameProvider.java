package com.mulesoft.connectors.inference.internal.llmmodels.vertexai.providers;

import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import com.mulesoft.connectors.inference.internal.llmmodels.vertexai.VertexAIModelName;

import java.util.Arrays;
import java.util.Set;

public class VertexAITextGenerationModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(VertexAIModelName.values())
        .filter(VertexAIModelName::isTextGenerationSupport).map(String::valueOf));
  }
}
