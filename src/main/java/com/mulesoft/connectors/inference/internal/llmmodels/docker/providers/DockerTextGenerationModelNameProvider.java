package com.mulesoft.connectors.inference.internal.llmmodels.docker.providers;

import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import com.mulesoft.connectors.inference.internal.llmmodels.docker.DockerModelName;

import java.util.Arrays;
import java.util.Set;

public class DockerTextGenerationModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(DockerModelName.values())
        .filter(DockerModelName::isTextGenerationSupport).map(String::valueOf));
  }
}
