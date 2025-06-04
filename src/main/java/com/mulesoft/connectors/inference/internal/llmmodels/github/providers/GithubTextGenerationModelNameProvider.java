package com.mulesoft.connectors.inference.internal.llmmodels.github.providers;

import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import com.mulesoft.connectors.inference.internal.llmmodels.github.GithubModelName;

import java.util.Arrays;
import java.util.Set;

public class GithubTextGenerationModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(GithubModelName.values())
        .filter(GithubModelName::isTextGenerationSupport).map(String::valueOf));
  }
}
