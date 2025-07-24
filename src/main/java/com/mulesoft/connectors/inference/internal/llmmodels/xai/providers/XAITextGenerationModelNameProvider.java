package com.mulesoft.connectors.inference.internal.llmmodels.xai.providers;

import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;

import com.mulesoft.connectors.inference.internal.llmmodels.xai.XAIModelName;

import java.util.Arrays;
import java.util.Set;

public class XAITextGenerationModelNameProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() {
    return ValueBuilder.getValuesFor(Arrays.stream(XAIModelName.values())
        .filter(XAIModelName::supportsTextGeneration).sorted().map(String::valueOf));
  }
}
