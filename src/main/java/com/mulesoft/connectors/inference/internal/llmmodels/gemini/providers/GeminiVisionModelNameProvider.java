package com.mulesoft.connectors.inference.internal.llmmodels.gemini.providers;

import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import com.mulesoft.connectors.inference.internal.llmmodels.gemini.GeminiModelName;

import java.util.Arrays;
import java.util.Set;

public class GeminiVisionModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(GeminiModelName.values())
        .filter(GeminiModelName::supportsVision).sorted().map(String::valueOf));
  }
}
