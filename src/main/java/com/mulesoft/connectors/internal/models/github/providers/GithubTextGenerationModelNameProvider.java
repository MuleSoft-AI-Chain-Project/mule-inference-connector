package com.mulesoft.connectors.internal.models.github.providers;

import com.mulesoft.connectors.internal.models.github.GithubModelName;
import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import java.util.Arrays;
import java.util.Set;

public class GithubTextGenerationModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(GithubModelName.values())
            .filter(GithubModelName::isTextGenerationSupport).map(String::valueOf));
  }
} 